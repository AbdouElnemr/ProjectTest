package elnemr.com.project.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import elnemr.com.project.R;

/**
 * Created by root on 3/28/16.
 */
public class MovieAdapter extends ArrayAdapter {
    Context mycontext;
    public MovieAdapter(Context context, int resource,List<Pojo> objects) {
        super(context, resource, objects);
        this.mycontext = context;

    }

    public class ViewHolder
    {
       ImageView imageView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Pojo pojo = (Pojo) getItem(position);
        LayoutInflater layoutInflater = (LayoutInflater) mycontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null )
        {
            convertView = layoutInflater.inflate(R.layout.image, null);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder) convertView.getTag();
            holder.imageView.setImageURI(Uri.parse("http://image.tmdb.org/t/p/w500/"+pojo.getImageurl()));
        }
        return convertView;
    }
}
