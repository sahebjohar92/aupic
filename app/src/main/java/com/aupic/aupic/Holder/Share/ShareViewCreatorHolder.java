package com.aupic.aupic.Holder.Share;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.aupic.aupic.Adaptors.Share.ShareUsAdaptor;
import com.aupic.aupic.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by saheb on 24/12/15.
 */
public class ShareViewCreatorHolder {

    @InjectView(R.id.listShareItems)
    ListView listShareItems;

    private Context context;

    public ShareViewCreatorHolder(Context context, View view) {

        ButterKnife.inject(this, view);
        this.context = context;
    }

    public void render(String fileName, String mimeType) {

        final Intent i = new Intent(Intent.ACTION_SEND);
        i.setType(mimeType);

        final Uri aupicUri = Uri.parse(fileName);

        final List<ResolveInfo> activities = context.getPackageManager().queryIntentActivities(i, 0);

        ShareUsAdaptor shareUsAdaptor = new ShareUsAdaptor(context, R.id.txtApp, activities,
                                                           context.getPackageManager());
        listShareItems.setAdapter(shareUsAdaptor);

        listShareItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                ResolveInfo info = (ResolveInfo) parent.getItemAtPosition(position);

                i.putExtra(Intent.EXTRA_STREAM, aupicUri);
                i.setPackage(info.activityInfo.packageName);
                context.startActivity(i);
            }

        });
    }
}
