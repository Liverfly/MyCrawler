package qh.cn.main;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import qh.cn.util.FileUtil;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class NewsCrawler extends BreadthCrawler{

	public static List<String> urls = new ArrayList<String>();
	/**
	 * @param crawlPath crawlPath is the path of the directory which maintains
	 * information of this crawler
	 * @param autoParse if autoParse is true,BreadthCrawler will auto extract
	 * links which match regex rules from pag
	 */
	public NewsCrawler(String crawlPath, boolean autoParse) {
		super(crawlPath, autoParse);
		/*����ҳ��*/
		//this.addSeed("http://news.hfut.edu.cn/list-1-1.html");
		this.addSeed("http://www.amazon.cn/s/rh=i%3Aamazon-global-store%2Ck%3A%E7%BE%8E%E8%B5%9E%E8%87%A3/ref=s9_acss_bw_fb_BabyAGSb_b2?_encoding=UTF8&bbn=1403206071&pf_rd_m=A1AJ19PSB66TGU&pf_rd_s=merchandised-search-4&pf_rd_r=1Z4ZMYX6M4TNPKFXRC4T&pf_rd_t=101&pf_rd_p=277014392&pf_rd_i=1494169071");

		/*�����������*/
		/*��ȡ���� http://news.hfut.edu.cn/show-xxxxxxhtml��URL*/
		//this.addRegex("http://news.hfut.edu.cn/show-.*html");
		this.addRegex("http://www(.+?)/dp/(.+)");
		/*��Ҫ��ȡ jpg|png|gif*/
		this.addRegex("-.*\\.(jpg|png|gif).*");
		/*��Ҫ��ȡ���� # ��URL*/
		this.addRegex("-.*#.*");
	}

	@Override
	public void visit(Page page, CrawlDatums next) {
		String url = page.getUrl();
		/*�ж��Ƿ�Ϊ����ҳ��ͨ��������������ж�*/
		if (page.matchUrl("http://www(.+?)/dp/(.+)")) {
			/*we use jsoup to parse page*/
			Document doc = page.getDoc();

			//extract title and content of news by css selector
			String title = page.select("span[id=productTitle]").text();
			String price = page.select("span[id=priceblock_ourprice]").text();

			urls.add(url);
			//System.out.println("URL:\n" + url);
			if(title!=null)
				System.out.println("title:\n" + title);
			if(price!=null)
				System.out.println("price:\n" + price);

			/*�����������µ���ȡ���񣬿�����next�������ȡ����
               ������������ᵽ���ֶ�����*/
			/*WebCollector���Զ�ȥ���ظ�������(ͨ�������key��Ĭ����URL)��
              ����ڱ�д����ʱ����Ҫ����ȥ�����⣬�����ظ���URL���ᵼ���ظ���ȡ*/
			/*���autoParse��true(���캯���ĵڶ�������)��������Զ���ȡ��ҳ�з�����������URL��
              ��Ϊ�������񣬵�Ȼ�������ȥ���ظ���URL��������ȡ��ʷ����ȡ����URL��
              autoParseΪtrue�������Զ���������*/
			//next.add("http://xxxxxx.com");
		}
	}

	public static void main(String[] args) throws Exception {
		NewsCrawler crawler = new NewsCrawler("crawl", true);
		/*�߳���*/
		crawler.setThreads(50);
		/*����ÿ�ε�������ȡ����������*/
		crawler.setTopN(5000);
		/*�����Ƿ�Ϊ�ϵ���ȡ���������Ϊfalse����������ǰ�������ʷ���ݡ�
           �������Ϊtrue����������crawlPath(���캯���ĵ�һ������)�Ļ����ϼ�
           ����ȡ�����ں�ʱ�ϳ������񣬺ܿ�����Ҫ��;�ж����棬Ҳ�п�������
           �������ϵ���쳣�����ʹ�öϵ���ȡģʽ�����Ա�֤���治����Щ����
           ��Ӱ�죬�����������Ϊ�жϡ��������ϵ��������ֺ󣬼�����ǰ������
           ������ȡ���ϵ���ȡĬ��Ϊfalse*/
		//crawler.setResumable(true);
		/*��ʼ���Ϊ4����ȡ��������Ⱥ���վ�����˽ṹû���κι�ϵ
            ���Խ�������Ϊ����������������������Խ�࣬��ȡ������Խ��*/
		crawler.start(2);
		String content = "";
		for(int i=0;i<urls.size();i++)
		{
			content += urls.get(i) + "\n";
		}
		FileUtil.createNewFile("result.txt", content);
	}
}
