package com.aupic.aupic.Adaptors.Share;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.aupic.aupic.R;

import java.util.List;

/**
 * Created by saheb on 17/5/15.
 */
public class ShareUsAdaptor extends ArrayAdapter<ResolveInfo> {

    Context context;
    PackageManager packageManager;


    public ShareUsAdaptor(Context context, int layoutId, List<ResolveInfo> apps, PackageManager pm) {
        super(context, layoutId, apps);
        this.context = context;
        this.packageManager = pm;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderShare viewHolderShare;

        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.share_us_list, parent, false);

                viewHolderShare = new ViewHolderShare();
                viewHolderShare.txtApp = (TextView) convertView.findViewById(R.id.txtApp);
                viewHolderShare.iconApp = (ImageView) convertView.findViewById(R.id.imgApp);

                convertView.setTag(viewHolderShare);
            } else {
                viewHolderShare = (ViewHolderShare) convertView.getTag();
            }


            viewHolderShare.txtApp.setText(getItem(position).loadLabel(packageManager).toString());
            viewHolderShare.iconApp.setImageDrawable(getItem(position).loadIcon(packageManager));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class ViewHolderShare {
        TextView txtApp;
        ImageView iconApp;
    }
}
