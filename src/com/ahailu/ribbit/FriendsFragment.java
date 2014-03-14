package com.ahailu.ribbit;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class FriendsFragment extends ListFragment {
	
	public static final String TAG = FriendsFragment.class.getSimpleName();
	
	protected List<ParseUser> mFriends;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        return rootView;
    }
	
	@Override
	public void onResume(){
		super.onResume();
		mCurrentUser = ParseUser.getCurrentUser();
		
		getActivity().setProgressBarIndeterminateVisibility(true);
		
		mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
		query.addAscendingOrder(ParseConstants.KEY_USERNAME);
		mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {
			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				getActivity().setProgressBarIndeterminateVisibility(false);
				if(e == null){
					mFriends = friends;
					
					String[] usernames = new String[mFriends.size()];
					int i = 0;
					for(ParseUser user : mFriends) {
						usernames[i] = user.getUsername();
						i++;
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), 
						android.R.layout.simple_list_item_1, usernames);
				setListAdapter(adapter);
				}
				else{
					// There was an error
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());
					// LOOK AT THIS STYLE
					builder.setMessage(e.getMessage()) // First this method is called
						.setTitle(R.string.error_title) // Then this method
						.setPositiveButton(android.R.string.ok, null); // And finally this, ended with a semi-colon
					AlertDialog dialog = builder.create();
					dialog.show(); 
				}

			}
		});
	}
	
	
}
