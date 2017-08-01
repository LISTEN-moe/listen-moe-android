package me.echeung.listenmoe.ui.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.echeung.listenmoe.R;
import me.echeung.listenmoe.constants.ResponseMessages;
import me.echeung.listenmoe.databinding.RadioFragmentBinding;
import me.echeung.listenmoe.interfaces.FavoriteSongListener;
import me.echeung.listenmoe.model.Song;
import me.echeung.listenmoe.ui.App;
import me.echeung.listenmoe.ui.activities.MainActivity;
import me.echeung.listenmoe.ui.fragments.base.TabFragment;
import me.echeung.listenmoe.util.APIUtil;
import me.echeung.listenmoe.util.AuthUtil;

public class RadioFragment extends TabFragment {

    @BindView(R.id.requested_by)
    TextView mRequestedByTxt;

    private Unbinder unbinder;

    public static Fragment newInstance(int sectionNumber) {
        return TabFragment.newInstance(sectionNumber, new RadioFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final RadioFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.radio_fragment, container, false);
        binding.setPlaying(App.STATE.playing);
        binding.setSong(App.STATE.currentSong);
        binding.setListeners(App.STATE.listeners);
        binding.setRequester(App.STATE.requester);

        final View view = binding.getRoot();
        unbinder = ButterKnife.bind(this, view);

        mRequestedByTxt.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @OnClick(R.id.play_pause_btn)
    public void togglePlayPause() {
        if (App.STATE.currentSong.get() == null) return;

        App.getService().togglePlayPause();
    }

    @OnClick(R.id.favorite_btn)
    public void favorite() {
        if (!AuthUtil.isAuthenticated(getActivity())) {
            ((MainActivity) getActivity()).showLoginDialog(this::favorite);
            return;
        }

        final Song currentSong = App.STATE.currentSong.get();
        if (currentSong == null) return;

        final int songId = currentSong.getId();
        if (songId == -1) return;

        APIUtil.favoriteSong(getActivity(), songId, new FavoriteSongListener() {
            @Override
            public void onFailure(final String result) {
                getActivity().runOnUiThread(() -> {
                    if (result.equals(ResponseMessages.AUTH_FAILURE)) {
                        Toast.makeText(getActivity(), getString(R.string.token_expired), Toast.LENGTH_SHORT).show();
                        ((MainActivity) getActivity()).showLoginDialog();
                    }
                });
            }

            @Override
            public void onSuccess(final boolean favorited) {
                App.STATE.currentSong.get().setFavorite(favorited);
            }
        });
    }
}