/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.senab.actionbarpulltorefresh.library.viewdelegates;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.view.View;
import android.widget.AbsListView;

import com.origamilabs.library.views.StaggeredGridView;

/**
 * Created By Mihir Joshi
 */
public class StaggeredDelegate extends PullToRefreshAttacher.ViewDelegate{

	public static final Class SUPPORTED_VIEW_CLASS = StaggeredGridView.class;

	@Override
	public boolean isReadyForPull(View view, final float x, final float y) {
		boolean ready = false;

		// First we check whether we're scrolled to the top
		StaggeredGridView gridView = (StaggeredGridView) view;
		if (gridView.getChildCount() == 0) {
			ready = true;
		} else if (gridView.getFirstPosition() == 0) {
			final View firstVisibleChild = gridView.getChildAt(0);
			ready = firstVisibleChild != null && firstVisibleChild.getTop() >= 0;
		}

		// Then we have to check whether the fas scroller is enabled, and check we're not starting
		// the gesture from the scroller


		return ready;
	}
}
