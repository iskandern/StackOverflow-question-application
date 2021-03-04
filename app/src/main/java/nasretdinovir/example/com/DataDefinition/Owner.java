package nasretdinovir.example.com.DataDefinition;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Owner implements Serializable {

    private static final long serialVersionUID = 7967409916403300347L;

    @SerializedName("profile_image")
    private String profileImage_;

    @SerializedName("display_name")
    private String displayName_;

    public Owner(String profileImage, String displayName) {
        profileImage_ = profileImage;
        displayName_ = displayName;
    }

    public String getDisplayName() {
        return displayName_;
    }

    public String getProfileImage() {
        return profileImage_;
    }
}
