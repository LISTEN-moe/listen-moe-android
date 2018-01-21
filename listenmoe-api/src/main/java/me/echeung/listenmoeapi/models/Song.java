package me.echeung.listenmoeapi.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song implements Parcelable {
    private int id;
    private String title;
    private String titleRomaji;
    private String titleSearchRomaji;
    private List<SongDescriptor> albums;
    private List<SongDescriptor> artists;
    private List<String> sources;
    private List<String> groups;
    private List<String> tags;
    private String notes;
    private int duration;
    private boolean favorite;
    private boolean enabled;
    private User uploader;

    public String getAlbumString() {
        StringBuilder s = new StringBuilder();
        if (albums != null) {
            for (SongDescriptor album : albums) {
                if (s.length() != 0)
                    s.append(", ");
                s.append(album.getName());
            }
        }
        return s.toString();
    }

    public String getArtistString() {
        StringBuilder s = new StringBuilder();
        if (artists != null) {
            for (SongDescriptor artist : artists) {
                if (s.length() != 0)
                    s.append(", ");
                s.append(artist.getName());
            }
        }
        return s.toString();
    }

    public String getSourceString() {
        StringBuilder s = new StringBuilder();
        if (sources != null) {
            for (String source : sources) {
                if (s.length() != 0)
                    s.append(", ");
                s.append(source);
            }
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getTitle(), getArtistString());
    }

    public String getAlbumArtUrl() {
        if (!albums.isEmpty()) {
            for (SongDescriptor album : albums) {
                if (album.getImage() != null) {
                    return "https://cdn.listen.moe/covers/" + album.getImage();
                }
            }
        }

        return null;
    }

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
            for (SongDescriptor album : albums) {
                if (album.getName() != null && album.getName().contains(query)) {
                    return true;
                }
                if (album.getNameRomaji() != null && album.getNameRomaji().contains(query)) {
                    return true;
                }
            }
        }

        if (artists != null) {
            for (SongDescriptor artist : artists) {
                if (artist.getName() != null && artist.getName().contains(query)) {
                    return true;
                }
                if (artist.getNameRomaji() != null && artist.getNameRomaji().contains(query)) {
                    return true;
                }
            }
        }

        if (groups != null) {
            for (String group : groups) {
                if (group != null && group.contains(query)) {
                    return true;
                }
            }
        }

        if (tags != null) {
            for (String tag : tags) {
                if (tag != null && tag.contains(query)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeTypedList(albums);
        parcel.writeTypedList(artists);
        parcel.writeStringList(sources);
        parcel.writeInt(duration);
        parcel.writeByte(favorite ? (byte) 1 : 0);
    }

    public Song(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        in.readTypedList(this.albums, SongDescriptor.CREATOR);
        in.readTypedList(this.artists, SongDescriptor.CREATOR);
        in.readStringList(this.sources);
        this.duration = in.readInt();
        this.favorite = in.readByte() == 1;
    }

    public static final Creator CREATOR = new Creator() {
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
