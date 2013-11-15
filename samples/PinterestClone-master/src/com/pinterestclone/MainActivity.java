package com.pinterestclone;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.actionbarsherlock.app.SherlockActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.origamilabs.library.views.StaggeredGridView;

public class MainActivity extends SherlockActivity
implements PullToRefreshAttacher.OnRefreshListener 
{
	private StaggeredGridView gridView = null;
    private PullToRefreshAttacher mPullToRefreshAttacher;

	private void openAlert()
	{
		new AlertDialog.Builder(this).setTitle("About").setMessage(Html.fromHtml(getString(R.string.copyright))).setCancelable(true).setPositiveButton("ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialogInterface, int which)
			{
				dialogInterface.dismiss();
			}
		}).create().show();
	}

	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		gridView.setColumnCount(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE ? 3:2);

	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);;
		setContentView(R.layout.activity_main);
		gridView = ((StaggeredGridView)findViewById(R.id.staggered_grid));
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));
		gridView.setAdapter(new StaggeredAdapter(getApplicationContext(), imageLoader));
		
		mPullToRefreshAttacher = PullToRefreshAttacher.get(this);

        // Retrieve the PullToRefreshLayout from the content view
        PullToRefreshLayout ptrLayout = (PullToRefreshLayout) findViewById(R.id.ptr_webview);

        // Give the PullToRefreshAttacher to the PullToRefreshLayout, along with the refresh
        // listener (this).
        ptrLayout.setPullToRefreshAttacher(mPullToRefreshAttacher, this);
        
        

	}

	@Override
	public void onRefreshStarted(View view) {

		new Thread()
		{
			public void run()
			{
				for (int i = 0; i <= 1000000; i++) {
					System.out.println(i);
					if(i==100000)
					{
						MainActivity.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								mPullToRefreshAttacher.setRefreshComplete();								
							}
						});
					}
					
				}
			}
		}.start();

	}


}