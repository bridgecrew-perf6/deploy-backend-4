package com.vtw.deploy.script.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zeroturnaround.zip.ZipUtil;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sshtools.net.SocketTransport;
import com.sshtools.sftp.FileTransferProgress;
import com.sshtools.sftp.SftpClient;
import com.sshtools.sftp.SftpStatusException;
import com.sshtools.sftp.TransferCancelledException;
import com.sshtools.ssh.ChannelOpenException;
import com.sshtools.ssh.SshClient;
import com.sshtools.ssh.SshConnector;
import com.sshtools.ssh.SshException;
import com.sshtools.ssh2.Ssh2PasswordAuthentication;
import com.vtw.deploy.script.config.EipsConfig;
import com.vtw.deploy.script.config.GmdadminConfig;
import com.vtw.deploy.script.exception.ScriptException;

/*
 * 파일관련 util
 * 
 * @author 이성정
 */
@Component
public class FileUtil {

	@Autowired
	private EipsConfig eipsConfig;

	@Autowired
	private GmdadminConfig gmdadminConfig;

	@Autowired
	private JschSessionUtil sessionUtil;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Transactional
	public int getFileFromSource(List<Map<String, String>> commandList, String source)
			throws JSchException, IOException, RuntimeException {

		Session session = null;
		
		String suCommand = "su - root";
		String suPassword = "";

		if (source.equals("jenkins")) {
			session = sessionUtil.createSession(eipsConfig.getHost(), eipsConfig.getUsername(),
					eipsConfig.getPassword());
			suPassword = "root";
		} else if (source.equals("gmdadmin")) {
			session = sessionUtil.createSession(gmdadminConfig.getHost(), gmdadminConfig.getUsername(),
					gmdadminConfig.getPassword());
			suPassword = "root";
		}

		int getFileResult = 0;

		ChannelExec channelExec = null;

		// 1.session을 받아서 exec채널을 연다.(ChannelExec는 SSH 용 채널 객체이다)
		channelExec = (ChannelExec) session.openChannel("exec");
		// 2.root계정으로 로그인하기위한 command
		channelExec.setCommand(suCommand);

		// 대화형으로 비밀번호를 입력하므로 inputStream outputStream을 생성한다. (버퍼에 담아두고 한번에 실행시키기 위해 bufferedstream생성)
		BufferedOutputStream bufferedOut = new BufferedOutputStream(channelExec.getOutputStream());
		BufferedInputStream bufferedIn = new BufferedInputStream(channelExec.getInputStream());
		channelExec.setErrStream(System.err);

		// 명령실행
		channelExec.connect();

		try {
			bufferedOut.write((suPassword + "\n").getBytes());
			bufferedOut.flush();
			
			for (Map<String, String> commandMap : commandList) {
				if (source.equals("jenkins")) {
					bufferedOut.write((commandMap.get("mkdirCommand") + "\n").getBytes());
					logger.info("out.write: " + commandMap.get("mkdirCommand"));
					bufferedOut.write((commandMap.get("jenkinsCommand") + "\n").getBytes());
					logger.info("out.write: " + commandMap.get("jenkinsCommand"));
				} else if (source.equals("gmdadmin")) {
					bufferedOut.write((commandMap.get("xmlSrc") + "\n").getBytes());
					logger.info("out.write: " + commandMap.get("xmlSrc"));
				}
			}

			bufferedOut.flush();
			bufferedOut.close();
			bufferedIn.read();
			bufferedIn.close();

			getFileResult = 1;

		} catch (Exception e) {
			throw new RuntimeException("jenkins에서 file복사하는것에 실패하였습니다 error:" + e.getMessage());
		} finally {
			channelExec.disconnect();
			session.disconnect();
		}
		return getFileResult;
	}

	@Transactional
	public void moveToOtherServer(String directory, String noneZipDirectory, String host, String userName,
			String password) throws IOException, SshException, SftpStatusException, ChannelOpenException,
			TransferCancelledException, RuntimeException {

		final String HOST = host;
		final String USER_NAME = userName;
		final String PASSWORD = password;
		final int PORT = 22;

		SshClient ssh = null;

		SocketTransport socket = new SocketTransport(HOST, PORT);
//		logger.info("host" + HOST);
//		logger.info("password" + PASSWORD);
//		logger.info("userName" + USER_NAME);
		try {
			SshConnector con = SshConnector.createInstance();
			ssh = con.connect(socket, USER_NAME);
			Ssh2PasswordAuthentication auth = new Ssh2PasswordAuthentication();
			auth.setUsername(USER_NAME);
			auth.setPassword(PASSWORD);
			
			int authResult = ssh.authenticate(auth);
			if (authResult != Ssh2PasswordAuthentication.COMPLETE) {
				throw new RuntimeException("authFail=" + authResult);
			}
			
			ssh.openSessionChannel().executeCommand("mkdir -p "+directory);
			ssh.openSessionChannel().close();
			
			SftpClient sftp = new SftpClient(ssh);
			// 서로다른 시스템에서 서로다른 데이터 포맷을 사용할 경우 target 에 맞는 형태로 변환되어야 함
			sftp.setTransferMode(SftpClient.MODE_BINARY);
			FileTransferProgress progress = createFileTransferProgress();

			sftp.copyRemoteDirectory(directory // 다운로드할 원격서버의 폴더
					, noneZipDirectory // 보낼 경로 ******이부분은 안바뀜, 상위경로 /home/../deployed-sources 부분이기때문에
					, true // recursive , true이면 하위폴더까지 포함
					, true // sync여부, true이면 원격폴더와 동기화(없는 파일은 삭제)
					, true // commit, true이면 실제작업 수행
					, progress);
			sftp.exit();

		} catch (SshException | IOException | SftpStatusException | ChannelOpenException | TransferCancelledException e) {
			logger.error(directory+" fail to download", e);
			throw new RuntimeException(e);
		} finally {
			if (ssh != null)
				try {
					ssh.disconnect();
				} catch (Exception e) {}
		}
	}// move to Gmdadmin end

	/*
	 * movetoGmdadmin의 progress를 보기위함
	 */
	public FileTransferProgress createFileTransferProgress() {
		return new FileTransferProgress() {

			@Override
			public void started(long arg0, String arg1) {
				logger.info("downloadstart:{}", arg1);
			}

			@Override
			public void progressed(long arg0) {
				logger.info("progress:{}", arg0);
			}

			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public void completed() {
				logger.info("download done");
			}
		};
	}

	/*
	 * zip파일 생성
	 * 
	 * @param sourceDir
	 * 
	 * @param targerZipFile
	 * 
	 * @throws IOException
	 * 
	 */
	public int makeZipFromDir(File sourceDir, File targetZipFile) throws ScriptException {

		int zipResult = 0;

		try {
			ZipUtil.pack(sourceDir, targetZipFile);
			zipResult = 1;
		} catch (Exception e) {
			throw new ScriptException("zip파일생성에 실패하였습니다. error:" + e.getMessage());
		}
		return zipResult;
	}
}