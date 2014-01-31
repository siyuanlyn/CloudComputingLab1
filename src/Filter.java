import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Entry{
	String articleName;
	int pageViews;
	public Entry(String newArticleName, int newPageViews){
		articleName = newArticleName;
		pageViews = newPageViews;
	}
}
public class Filter {
	
	final static String[] EXCLUDE_STRING = {
		"Media:",
		"Special:",
		"Talk:",
		"User:",
		"User_talk:",
		"Project:",
		"Project_talk:",
		"File:",
		"File_talk:",
		"MediaWiki:",
		"MediaWiki_talk:",
		"Template:",
		"Template_talk:",
		"Help:",
		"Help_talk:",
		"Category:",
		"Category_talk:",
		"Portal:",
		"Wikipedia:",
		"Wikipedia_talk:"
	};
	final static String[] ILLEGAL_EXTENSIONS = {
		".jpg", ".gif", ".png", ".JPG", ".GIF", ".PNG", ".txt", ".ico"
	};
	final static String[] MEDIAWIKI = {
		"404_error/",
		"Main_Page",
		"Hypertext_Transfer_Protocol",
		"Favicon.ico",
		"Search"
	};
	
	
	public static void main(String[] args) throws IOException{
		InputStream is = new FileInputStream("D:\\Dropbox\\pagecounts");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		OutputStream os = new FileOutputStream("D:\\Dropbox\\output.txt");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
		ArrayList<Entry> al = new ArrayList<Entry>();
		while(true){
			String line = br.readLine();
			if(line == null){
				break;
			}
			String[] s = line.split(" ");
			if(s.length < 4){
				continue;
			}
			//entry should start with "en"
//			if(s[0].length() != 2 || !s[0].substring(0, 2).equals("en")){
//				continue;
//			}
			if(!s[0].equals("en")){
				continue;
			}
			//filter illegal title out
			int i;
			for(i=0; i<EXCLUDE_STRING.length; i++){
				if(s[1].contains(EXCLUDE_STRING[i])){
					break;
				}
			}
			if(i != EXCLUDE_STRING.length){	//something matched
				continue;
			}
			//filter out the article with title started with lower case
			if(s[1].substring(0, 1).matches("[a-z]")){
				continue;
			}
			//filter out the file with illegal extensions
			for(i=0; i<ILLEGAL_EXTENSIONS.length; i++){
				if(!(s[1].contains(ILLEGAL_EXTENSIONS[i]) || s[1].contains(ILLEGAL_EXTENSIONS[i].toLowerCase()))){
					continue;
				}
				break;
			}
			if(i != ILLEGAL_EXTENSIONS.length){	//something matched
				continue;
			}
			//exclude mediawiki entry
			for(i=0; i<MEDIAWIKI.length; i++){
				if(s[1].contains(MEDIAWIKI[i])){
					break;
				}
			}
			if(i != MEDIAWIKI.length){	//something matched
				continue;
			}
			
			//legal entry survive at the end
			al.add(new Entry(s[1], Integer.parseInt(s[2])));
		}
		Collections.sort(al, new Comparator<Entry>(){
			public int compare(Entry e1, Entry e2){
				if(e1.pageViews > e2.pageViews){
					return -1;
				}
				else if (e1.pageViews < e2.pageViews){
					return 1;
				}
				else{
					return 0;
				}	
			}
		});
		for(Entry e : al){
			bw.write(e.articleName + "\t" + e.pageViews + "\n");
			bw.flush();
		}
		
	}
}
