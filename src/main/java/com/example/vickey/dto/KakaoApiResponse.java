package com.example.vickey.dto;

public class KakaoApiResponse {
    private String id;
    private KakaoAccount kakaoAccount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public KakaoAccount getKakaoAccount() {
        return kakaoAccount;
    }

    public void setKakaoAccount(KakaoAccount kakaoAccount) {
        this.kakaoAccount = kakaoAccount;
    }

    public String getNickname() {
        if (kakaoAccount != null && kakaoAccount.profile != null) {
            return kakaoAccount.profile.nickname;
        }
        return "Unknown";
    }

    public String getEmail() {
        return kakaoAccount.email;
    }

    public String getProfileImageUrl() {
        if (kakaoAccount != null && kakaoAccount.profile != null) {
            return kakaoAccount.profile.profileImageUrl;
        }
        return "";
    }

    public String getThumbnailImageUrl() {
        return kakaoAccount.profile.thumbnailImageUrl;
    }


    public static class KakaoAccount {
        private String email;
        private Profile profile;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public static class Profile {
            private String nickname;
            private String profileImageUrl;
            private String thumbnailImageUrl;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getProfileImageUrl() {
                return profileImageUrl;
            }

            public void setProfileImageUrl(String profileImageUrl) {
                this.profileImageUrl = profileImageUrl;
            }

            public String getThumbnailImageUrl() {
                return thumbnailImageUrl;
            }

            public void setThumbnailImageUrl(String thumbnailImageUrl) {
                this.thumbnailImageUrl = thumbnailImageUrl;
            }
        }
    }
}
