package top.yukino.base;

/***
 * 启动调度
 * 
 * @author 13723
 *
 */
public class Application {
	public static void main(String args[]) throws Exception {
		if (args.length == 0) {
			help();
			return;
		}
		switch (args[0]) {
		case "--fec":
			if (args.length != 5)
				System.err.println("--fec参数错误,[--fec * * * *] 输入文件，输出路径,输入编码，输出编码");
			else
				top.yukino.base.tool.LangCode.FileCode(args[1], args[2], args[3], args[4]);
			break;
		default:
			help();
			break;
		}
	}

	/**
	 * 打印帮助信息
	 */
	public static void help() {
		StringBuilder builder = new StringBuilder();
		builder.append("Ibkon java bases\n");
		builder.append("===>本程序为个人练习编写，功能组合较为杂乱，参数解析为固定。\n");
		builder.append("===>基本使用方法，目前仅提供命令行使用。\n");
		builder.append("--fec * * * *	文件编码功能（输入文件，输出路径,输入编码，输出编码）。\n");

		System.out.println(builder);
	}
}
