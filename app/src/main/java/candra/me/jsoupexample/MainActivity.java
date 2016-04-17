package candra.me.jsoupexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView txtTitle;
    NewsAdapter newsAdapter;
    ProgressDialog mProgressDialog;
    private static String URL = "http://finance.detik.com/indeksfeatured";
    ArrayList<News> arrayList = new ArrayList<>();
    String webTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_news);
        txtTitle = (TextView) findViewById(R.id.txt_title);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Jsoup example");
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Connection con = Jsoup.connect(URL)
                        .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
                        .timeout(10000);
                Connection.Response resp = con.execute();
                Document doc = null;
                if (resp.statusCode() == 200) {
                    doc = con.get();
                    webTitle = doc.title();
                    Elements els = doc.getElementsByClass("f12");

                    for (Element el : els) {
                        News news = new News();
                        news.setImg(el.select("a img[src]").attr("src"));
                        news.setTitle(el.getElementsByClass("judul").select("a").html());
                        TextNode next = (TextNode) el.select("h4.judul").first().nextSibling();
                        news.setDescription(next.text());

                        arrayList.add(news);
                    }

                    Log.d("element count ","" + els.size());

                }




            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
            txtTitle.setText(webTitle);
            newsAdapter = new NewsAdapter(MainActivity.this, arrayList);
            listView.setAdapter(newsAdapter);
        }
    }
}
