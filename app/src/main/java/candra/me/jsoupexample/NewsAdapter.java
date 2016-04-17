package candra.me.jsoupexample;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by candra on 16-Apr-16.
 */
public class NewsAdapter extends BaseAdapter {

    Context context;
    ArrayList<News> arrayList;
    LayoutInflater inflater;

    public NewsAdapter(Context context, ArrayList<News> arrayList){
        this.context = context;
        this.arrayList = arrayList;
        inflater = ((Activity) context).getLayoutInflater();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public News getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        News news = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.news_item_layout, null);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txtDescription = (TextView) convertView.findViewById(R.id.txt_description);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTitle.setText(Html.fromHtml(news.getTitle()));
        holder.txtDescription.setText(Html.fromHtml(news.getDescription()));
        Picasso.with(context).load(news.getImg()).into(holder.img);

        return convertView;
    }

    class ViewHolder {
        TextView txtTitle, txtDescription;
        ImageView img;
    }
}
