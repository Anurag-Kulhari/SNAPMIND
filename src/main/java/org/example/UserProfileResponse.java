package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties   (ignoreUnknown = true)
public class UserProfileResponse {
    private String user_id;
    private BasicDemographics basic_demographics;
    private FacialFeatures facial_features;
    private EmotionalStateAndPersonalityTraits emotional_state_and_personality_traits;
    private AccessoriesPreferences accessories_preferences;
    private ApparelPreferences apparel_preferences;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public BasicDemographics getBasic_demographics() {
        return basic_demographics;
    }

    public void setBasic_demographics(BasicDemographics basic_demographics) {
        this.basic_demographics = basic_demographics;
    }

    public FacialFeatures getFacial_features() {
        return facial_features;
    }

    public void setFacial_features(FacialFeatures facial_features) {
        this.facial_features = facial_features;
    }

    public EmotionalStateAndPersonalityTraits getEmotional_state_and_personality_traits() {
        return emotional_state_and_personality_traits;
    }

    public void setEmotional_state_and_personality_traits(EmotionalStateAndPersonalityTraits emotional_state_and_personality_traits) {
        this.emotional_state_and_personality_traits = emotional_state_and_personality_traits;
    }

    public AccessoriesPreferences getAccessories_preferences() {
        return accessories_preferences;
    }

    public void setAccessories_preferences(AccessoriesPreferences accessories_preferences) {
        this.accessories_preferences = accessories_preferences;
    }

    public ApparelPreferences getApparel_preferences() {
        return apparel_preferences;
    }

    public void setApparel_preferences(ApparelPreferences apparel_preferences) {
        this.apparel_preferences = apparel_preferences;
    }

    // Getters and setters
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BasicDemographics {
        private String age;
        private String gender;

        // Getters and setters

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FacialFeatures {
        private String skin_type;
        private String skin_tone;
        private String skin_undertone;
        private String facial_shape;
        private String eye_color;
        private String lip_shape;
        private String hair_type;
        private String hair_length;
        private String hair_color;
        private String beard_presence;
        private String beard_style;
        private String beard_density;
        private String beard_color;

        public String getSkin_type() {
            return skin_type;
        }

        public void setSkin_type(String skin_type) {
            this.skin_type = skin_type;
        }

        public String getSkin_tone() {
            return skin_tone;
        }

        public void setSkin_tone(String skin_tone) {
            this.skin_tone = skin_tone;
        }

        public String getSkin_undertone() {
            return skin_undertone;
        }

        public void setSkin_undertone(String skin_undertone) {
            this.skin_undertone = skin_undertone;
        }

        public String getFacial_shape() {
            return facial_shape;
        }

        public void setFacial_shape(String facial_shape) {
            this.facial_shape = facial_shape;
        }

        public String getEye_color() {
            return eye_color;
        }

        public void setEye_color(String eye_color) {
            this.eye_color = eye_color;
        }

        public String getLip_shape() {
            return lip_shape;
        }

        public void setLip_shape(String lip_shape) {
            this.lip_shape = lip_shape;
        }

        public String getHair_type() {
            return hair_type;
        }

        public void setHair_type(String hair_type) {
            this.hair_type = hair_type;
        }

        public String getHair_length() {
            return hair_length;
        }

        public void setHair_length(String hair_length) {
            this.hair_length = hair_length;
        }

        public String getHair_color() {
            return hair_color;
        }

        public void setHair_color(String hair_color) {
            this.hair_color = hair_color;
        }

        public String getBeard_presence() {
            return beard_presence;
        }

        public void setBeard_presence(String beard_presence) {
            this.beard_presence = beard_presence;
        }

        public String getBeard_style() {
            return beard_style;
        }

        public void setBeard_style(String beard_style) {
            this.beard_style = beard_style;
        }

        public String getBeard_density() {
            return beard_density;
        }

        public void setBeard_density(String beard_density) {
            this.beard_density = beard_density;
        }

        public String getBeard_color() {
            return beard_color;
        }

        public void setBeard_color(String beard_color) {
            this.beard_color = beard_color;
        }
        // Getters and setters
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EmotionalStateAndPersonalityTraits {
        private String mood;
        private String energy;
        private String confidence_level;

        // Getters and setters

        public String getMood() {
            return mood;
        }

        public void setMood(String mood) {
            this.mood = mood;
        }

        public String getEnergy() {
            return energy;
        }

        public void setEnergy(String energy) {
            this.energy = energy;
        }

        public String getConfidence_level() {
            return confidence_level;
        }

        public void setConfidence_level(String confidence_level) {
            this.confidence_level = confidence_level;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessoriesPreferences {
        private String jewelry;
        private String eyewear;
        private String hats_and_caps;

        // Getters and setters

        public String getJewelry() {
            return jewelry;
        }

        public void setJewelry(String jewelry) {
            this.jewelry = jewelry;
        }

        public String getEyewear() {
            return eyewear;
        }

        public void setEyewear(String eyewear) {
            this.eyewear = eyewear;
        }

        public String getHats_and_caps() {
            return hats_and_caps;
        }

        public void setHats_and_caps(String hats_and_caps) {
            this.hats_and_caps = hats_and_caps;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ApparelPreferences {
        private String style;
        private String color;
        private String pattern_preference;

        // Getters and setters


        public String getStyle() {
            return style;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getPattern_preference() {
            return pattern_preference;
        }

        public void setPattern_preference(String pattern_preference) {
            this.pattern_preference = pattern_preference;
        }
    }

}
