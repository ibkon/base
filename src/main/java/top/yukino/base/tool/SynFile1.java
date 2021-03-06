package top.yukino.base.tool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import org.junit.Test;

import top.yukino.base.bean.FileInfo;

/***
 * 文件夹间数据同步处理 所有方法in，out参数默认为本地目录 MOD设置MOD_NET_IN或MOD_NET_OUT则相应为服务器和客户端
 * 当设置MOD_NET_IN时，in格式为监听端口，out为下一个网络地址或本地目录
 * 当设置MOD_NET_OUT时，in为本地保存目录，out格式为"127.0.0.1:1150/path"或"yukino.top:1150/path"
 */
public class SynFile1 {
	@Test
	public void test() {
		SynFile1 syn = new SynFile1();
		syn.backup("E:\\JavaWeb\\blog", "E:\\JavaWeb\\backup", MOD_COPY);
	}

	public static final int MOD_NET_IN = 1; // 设置为监听服务器 判断优先级0
	public static final int MOD_NET_OUT = 2; // 设置为发送客户端 判断优先级0
	public static final int MOD_COPY = 4; // 复制并覆盖所有文件 判断优先级1
	public static final int MOD_ASK	= 8;	//当输出路径存在相同文件询问处理方式
	public static final int MOD_COVER = 16;	//当输出路径存在相同文件直接覆盖
	
	private List<File> fList;
	private byte[] buffer;
	private String inPath, outPath;

	public SynFile1() {
		this.fList = new ArrayList<File>();
		this.buffer = new byte[0x20000];
	}

	/**
	 * 备份程序，支持支持网络备份
	 * 
	 * @param in  输入
	 * @param out 输出
	 * @param mod 处理模式
	 */
	public void backup(String in, String out, int mod) {
		this.inPath = in;
		this.outPath = out;
		// 设置默认目录
		if (in == null || in.length() == 0) {
			this.inPath = ".";
		}
		if (out == null || out.length() == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd");
			this.outPath = "backup" + format.format(new Date());
		}
		loadPathList(new File(in));
		if ((MOD_NET_IN & mod) > 0 || (MOD_NET_OUT & mod) > 0) {
			// 网络处理
		} else {
			// 本地处理
		}
	}

	/**
	 * 加载一个目录下所有文件和目录
	 * 
	 * @param path
	 */
	private void loadPathList(File path) {
		// 仅当输入为一个目录时才进行加载
		if (path != null && path.exists() && path.isDirectory()) {
			if (path.list().length == 0) {
				this.fList.add(path);
			} else {
				for (File f : path.listFiles()) {
					if (f.isFile()) {
						this.fList.add(f);
					} else {
						loadPathList(f);
					}
				}
			}
		}
	}
	
	private void loadFileHash() {
		if(this.fList.size()>0) {
			
		}
	}
	

	/***
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public static final int MOD_FILE = 0x0001; // 文件
	public static final int MOD_EDIR = 0x0002; // 目录
	public static final int MOD_CHANGE = 0x0004; // 修改===废弃
	public static final int MOD_HASH = 0x0008; // 文件内容验证
	public static final int MOD_NAME = 0x0010; // 根据文件名验证，如果未选择HASH验证则默认选择该方法验证

	private static final int C_IN = 0x0100; // 输入文件控制,默认为输入处理，不需要填写
	private static final int C_OUT = 0x0200; // 输出文件控制
	private static final int C_CREATE = 0x0400; // 重新创建容器控制

	private int inLength;
	private List<FileInfo> listDir;

	private List<FileInfo> loadList;

	/**
	 * 根据输入目录的文件删除输出目录的文件
	 * 
	 * @param in  输入目录
	 * @param out 输出目录
	 * @param mod 删除模式
	 */
	public void del(String in, String out, int mod) {
		List<FileInfo> listIn, listOut;
		List<String> listDir;
		Set<FileInfo> result;
		File inFile;
		read(inFile = new File(in));
		listIn = getLoadList();
		// 获取输入目录的目录
		listDir = new ArrayList<>();
		listOut = new ArrayList<>();
		for (File f : new File(out).listFiles()) {
			if (f.isFile()) {
				listOut.add(new FileInfo(null, null, f.getAbsolutePath(), true));
			}
		}
		for (File f : inFile.listFiles()) {
			if (f.isDirectory()) {
				listDir.add(f.getName());
			}
		}
		for (String s : listDir) {
			read(new File(out.charAt(out.length() - 1) == '\\' ? out + s : out + '\\' + s));
			listOut.addAll(getLoadList());
		}
		// 为结果集申请内存
		result = new HashSet<>();
		if ((MOD_HASH & mod) == MOD_HASH) {
			/*
			 * 根据文件内容删除文件
			 */

			// 使用多线程计算HASH
			TheadHash<FileInfo> hash = new TheadHash<FileInfo>() {
				@Override
				public String runHash(FileInfo obj) {
					File file = new File(obj.getAbsolutePath());
					if (file.isFile())
						return fHash(file, "SHA256");
					else
						return null;
				}

				@Override
				public Object getObjID(FileInfo obj) {
					return obj.getAbsolutePath();
				}
			};
			// HASH结果集
			Map<Object, String> min, mout;
			min = hash.start(listIn);
			mout = hash.start(listOut);
			for (FileInfo f : listIn) {
				f.setHash(min.get(f.getAbsolutePath()));
			}
			for (FileInfo f : listOut) {
				f.setHash(mout.get(f.getAbsolutePath()));
			}
			for (FileInfo o : listOut) {
				for (FileInfo i : listIn) {
					if (o.getHash().equals(i.getHash())) {
						result.add(o);
						break;
					}
				}
			}
		} else {
			// 确保如果不使用hash验证则使用文件名进行验证
			mod |= MOD_NAME;
		}

		if ((MOD_NAME & mod) == MOD_NAME) {
			/*
			 * 根据文件名删除文件
			 */
			int inLength = in.length();
			int outLength = out.length();
			if (in.charAt(inLength - 1) != '\\')
				inLength++;
			if (out.charAt(outLength - 1) != '\\')
				outLength++;
			// 使用相对路径进行对比
			for (FileInfo o : listOut) {
				o.setRelativePath(o.getAbsolutePath().substring(outLength));
				for (FileInfo i : listIn) {
					if (i.getRelativePath() == null) {
						i.setRelativePath(i.getAbsolutePath().substring(inLength));
					}
					if (o.getRelativePath().equals(i.getRelativePath())) {
						result.add(o);
					}
				}
			}
		}
		// 对文件进行删除
		for (FileInfo f : result) {
			if (new File(f.getAbsolutePath()).delete()) {
				// System.out.println("成功删除文件:"+f.getRelativePath());
			} else {
				// System.out.println("文件删除失败:"+f.getAbsolutePath());
			}
		}
		if ((MOD_EDIR & mod) == MOD_EDIR) {
			/*
			 * 删除目录
			 */
			for (String s : listDir) {
				delDir(out.charAt(out.length() - 1) == '\\' ? out + s : out + '\\' + s);
			}
		}
	}

	/**
	 * 递归删除空目录
	 * 
	 * @param dir 需要删除的起始目录，删除包括该目录
	 */
	private void delDir(String dir) {
		File file = new File(dir);
		File temp = null;
		if (file.isDirectory()) {
			if (file.list().length != 0) {
				for (String s : file.list()) {
					delDir(s);
				}
			}
			temp = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('\\')) + "\\del");
			file.renameTo(temp);
			if (temp.delete()) {
				// System.out.println("成功删除目录:"+temp.getAbsolutePath());
			} else {
				// System.out.println("目录删除失败:"+temp.getAbsolutePath());
			}
		}
	}

	/**
	 * 文件对比，需要obj包含in
	 * 
	 * @param in  对比源文件
	 * @param obj 需要比较的文件
	 * @return
	 */
	public List<String> compare(String in, String obj) {
		return null;
	}

	/**
	 * 读取目录
	 * 
	 * @param file 需要读取的根目录
	 * @param mod  需要读取的类型
	 */
	private void read(File file, int mod) {
		if ((C_OUT & mod) == C_OUT) {
			System.out.println("IN out " + mod);
			// 读取需要对比的文件内容
			if (this.listDir.size() > 0) {
				List<FileInfo> out = new ArrayList<>();
				String path = file.getAbsolutePath();
				File temp = null;
				FileInfo info = null;
				if (path.charAt(path.length() - 1) != '\\') {
					path += '\\';
				}
				if ((MOD_HASH & mod) == MOD_HASH) {
					// 根据HASH值存取
					List<FileInfo> back = this.listDir;
					read(file, mod - C_OUT);
					for (FileInfo i : this.listDir) {
						for (FileInfo o : back) {
							if (i.getHash().equals(o.getHash())) {
								out.add(i);
							}
						}
					}
				} else {
					for (FileInfo i : this.listDir) {
						temp = new File(path + i.getRelativePath());
						info = new FileInfo();
						info.setAbsolutePath(temp.getAbsolutePath());
						info.setRelativePath(i.getRelativePath());
						if (i.isFile() && temp.isFile()) {
							info.setFile(true);
							out.add(info);
						}
						if (!i.isFile() && (temp.list().length == 0)) {
							out.add(info);
						}
					}
				}
				this.listDir = out;
			}
			return;
		}

		/*
		 * 递归获取文件和目录
		 */

		if ((C_CREATE & mod) == C_CREATE) {
			// 重新创建容器
			this.listDir = new ArrayList<>();
			mod -= C_CREATE;
		}
		if (((MOD_FILE & mod) == MOD_FILE) && file.isFile()) {
			// 读取文件
			FileInfo info = new FileInfo();
			info.setAbsolutePath(file.getAbsolutePath());
			info.setRelativePath(file.getAbsolutePath().substring(mod >>> 24));
			if ((MOD_HASH & mod) == MOD_HASH)
				info.setHash(Hash.fHash(file, "SHA256"));
			info.setFile(true);
			this.listDir.add(info);
		}
		if (((MOD_EDIR & mod) == MOD_EDIR) && file.isDirectory() && file.list().length == 0) {
			// 读取空目录
			FileInfo info = new FileInfo();
			info.setAbsolutePath(file.getAbsolutePath());
			info.setRelativePath(file.getAbsolutePath().substring(mod >>> 24));
			info.setFile(false);
			this.listDir.add(info);
		}
		if (file.isDirectory() && file.list().length > 0) {
			for (File f : file.listFiles()) {
				read(f, mod);
			}
		}
	}

	/**
	 * 读取file目录下所有路径
	 * 
	 * @param file 需要读取的相对根目录
	 */
	public void read(File file) {
		if (this.loadList == null) {
			this.loadList = new ArrayList<>();
		}
		if (file.isFile()) {
			loadList.add(new FileInfo(null, null, file.getAbsolutePath(), true));
		} else if (file.isDirectory() && file.list().length == 0) {
			loadList.add(new FileInfo(null, null, file.getAbsolutePath(), false));
		} else {
			for (File f : file.listFiles()) {
				read(f);
			}
		}
	}

	public List<FileInfo> getLoadList() {
		List<FileInfo> backup = this.loadList;
		this.loadList = null;
		return backup;
	}
}
