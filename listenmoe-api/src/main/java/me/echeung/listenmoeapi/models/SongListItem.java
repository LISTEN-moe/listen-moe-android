package me.echeung.listenmoeapi.models;

import java.util.List;

import lombok.Getter;

@Getter
public class SongListItem {
    private List<String> albums;
    private List<Integer> albumsId;
    private List<String> albumsCover;
    private List<String> albumsReleaseDate;
    private List<String> albumsRomaji;
    private List<String> albumsSearchRomaji;
    private List<Integer> albumsTrackNumber;
    private List<Integer> albumsType;
    private List<String> artists;
    private List<Integer> artistsId;
    private List<String> artistsRomaji;
    private List<String> artistsSearchRomaji;
    private int duration;
    private boolean enabled;
    private boolean favorite;
    private List<String> groups;
    private List<Integer> groupsId;
    private List<String> groupsRomaji;
    private List<String> groupsSearchRomaji;
    private int id;
    private String lastPlayed;
    private String snippet;
    private List<String> sources;
    private List<String> sourcesRomaji;
    private List<String> tags;
    private String title;
    private String titleRomaji;
    private String titleSearchRomaji;
    private String uploaderUuid;
    private String uploaderUsername;
    private String uploaderDisplayName;

    public boolean search(String query) {
        if (title != null && title.contains(query)) {
            return true;
        }

        if (titleRomaji != null && titleRomaji.contains(query)) {
            return true;
        }

        if (titleSearchRomaji != null && titleSearchRomaji.contains(query)) {
            return true;
        }

        if (albums != null) {
            for (String album : albums) {
                if (album.contains(query)) {
                    return true;
                }
            }
        }

        if (albumsRomaji != null) {
            for (String album : albumsRomaji) {
                if (album.contains(query)) {
                    return true;
                }
            }
        }

        if (albumsSearchRomaji != null) {
            for (String album : albumsSearchRomaji) {
                if (album.contains(query)) {
                    return true;
                }
            }
        }

        if (artists != null) {
            for (String artist : artists) {
                if (artist.contains(query)) {
                    return true;
                }
            }
        }

        if (artistsRomaji != null) {
            for (String artist : artistsRomaji) {
                if (artist.contains(query)) {
                    return true;
                }
            }
        }

        if (artistsSearchRomaji != null) {
            for (String artist : artistsSearchRomaji) {
                if (artist.contains(query)) {
                    return true;
                }
            }
        }

        if (groups != null) {
            for (String group : groups) {
                if (group.contains(query)) {
                    return true;
                }
            }
        }

        if (groupsRomaji != null) {
            for (String group : groupsRomaji) {
                if (group.contains(query)) {
                    return true;
                }
            }
        }

        if (groupsSearchRomaji != null) {
            for (String group : groupsSearchRomaji) {
                if (group.contains(query)) {
                    return true;
                }
            }
        }

        return false;
    }
}