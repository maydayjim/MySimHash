//package main;
//
//import java.math.BigDecimal;
//import main.Clibrary;
//import com.sun.jna.Native;
//
//public class Demo {
//
//
//    public static void main(String[] args) throws Exception {
//        //初始化
//        Clibrary instance = (Clibrary)Native.loadLibrary(System.getProperty("user.dir")+"\\source\\NLPIR", Clibrary.class);
//        int init_flag = instance.NLPIR_Init("", 1, "0");
//        String resultString = null;
//        if (0 == init_flag) {
//            resultString = instance.NLPIR_GetLastErrorMsg();
//            System.err.println("初始化失败！\n"+resultString);
//            return;
//        }
//
//        String sInput = "哎~那个金刚圈尺寸太差，前重后轻，左宽右窄，他戴上去很不舒服，"
//                + "整晚失眠会连累我嘛，他虽然是只猴子，但你也不能这样对他啊，官府知道会说我虐待动物的，"
//                + "说起那个金刚圈，啊~去年我在陈家村认识了一个铁匠，他手工精美，价钱又公道，童叟无欺，"
//                + "干脆我介绍你再定做一个吧！";
//
//        try {
//            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
//            System.out.println("分词结果为：\n " + resultString);
//
//            instance.NLPIR_AddUserWord("金刚圈");
//            instance.NLPIR_AddUserWord("左宽右窄");
//            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
//            System.out.println("增加用户词典后分词结果为：\n" + resultString);
//
//            instance.NLPIR_DelUsrWord("左宽右窄");
//            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
//            System.out.println("删除用户词典后分词结果为：\n" + resultString);
//
//            instance.NLPIR_ImportUserDict(System.getProperty("user.dir")+"\\source\\userdic.txt");
//            resultString = instance.NLPIR_ParagraphProcess(sInput, 1);
//            System.out.println("导入用户词典文件后分词结果为：\n" + resultString);
//
//            resultString = instance.NLPIR_GetKeyWords(sInput,10,false);
//            System.out.println("从段落中提取的关键词：\n" + resultString);
//
//            resultString = instance.NLPIR_GetNewWords(sInput, 10, false);
//            System.out.println("新词提取结果为：\n" + resultString);
//
//            Double d = instance.NLPIR_FileProcess("D:\\1.txt", "D:\\2.txt", 1);
//
//            System.out.println("对文件内容进行分词的运行速度为： " );
//            if(d.isInfinite())
//                System.out.println("无结果");
//            else{
//                BigDecimal b = new BigDecimal(d);
//                System.out.println(b.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP)+"秒");
//            }
//            resultString = instance.NLPIR_GetFileKeyWords("D:\\3.txt", 10,false);
//            System.out.println("从文件中提取关键词的结果为：\n" + resultString);
//
//            instance.NLPIR_Exit();
//
//        } catch (Exception e) {
//            System.out.println("错误信息：");
//            e.printStackTrace();
//        }
//
//    }
//}