/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidclub.source;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * A listener which expects a URL as a tag of the view it is associated with. It
 * then opens the URL in the browser application.
 */
public class UrlIntentListener implements OnItemClickListener {

	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		final String url = view.getTag().toString();
		final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		final Context context = parent.getContext();
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		
		boolean isIntentSafe = activities.size() > 0;
		if (isIntentSafe) {
			context.startActivity(intent);
		}
		else {
			Dialog d = new Dialog(context);
			d.setTitle("Error");
			TextView tv = new TextView(context);
			tv.setText("No application to handle your request");
			d.setContentView(tv);
			d.show();
		}
	}

}
