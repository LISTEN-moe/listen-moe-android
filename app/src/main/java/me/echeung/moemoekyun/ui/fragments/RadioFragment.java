package me.echeung.moemoekyun.ui.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.echeung.moemoekyun.R;
import me.echeung.moemoekyun.databinding.RadioFragmentBinding;
import me.echeung.moemoekyun.service.StreamService;
import me.echeung.moemoekyun.state.AppState;
import me.echeung.moemoekyun.ui.activities.MainActivity;
import me.echeung.moemoekyun.ui.fragments.base.TabFragment;
import me.echeung.moemoekyun.util.AuthUtil;

public class RadioFragment extends TabFragment {

    public static Fragment newInstance(int sectionNumber) {
        return TabFragment.newInstance(sectionNumber, new RadioFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RadioFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.radio_fragment, container, false);
        binding.setPlaying(AppState.getInstance().playing);
        binding.setSong(AppState.getInstance().currentSong);
        binding.setFavorited(AppState.getInstance().currentFavorited);
        binding.setListeners(AppState.getInstance().listeners);
        binding.setRequester(AppState.getInstance().requester);

        final View view = binding.getRoot();

        binding.requestedBy.setMovementMethod(LinkMovementMethod.getInstance());
        binding.playPauseBtn.setOnClickListener(v -> togglePlayPause());
        // TODO: show history items in bottom sheet?
//        binding.historyBtn.setOnClickListener(v -> favorite());
        binding.favoriteBtn.setOnClickListener(v -> favorite());

        return view;
    }

    private void togglePlayPause() {
        final Intent playPauseIntent = new Intent(StreamService.PLAY_PAUSE);
        getActivity().sendBroadcast(playPauseIntent);
    }

    private void favorite() {
        if (!AuthUtil.isAuthenticated(getActivity())) {
            ((MainActivity) getActivity()).showLoginDialog(this::favorite);
            return;
        }

        final Intent favIntent = new Intent(StreamService.TOGGLE_FAVORITE);
        getActivity().sendBroadcast(favIntent);
    }
}