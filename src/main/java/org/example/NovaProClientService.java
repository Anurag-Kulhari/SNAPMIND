package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.List;

@Service
public class NovaProClientService {

    private BedrockRuntimeClient bedrockRuntimeClient;

    public NovaProClientService(BedrockRuntimeClient bedrockClient) {
        this.bedrockRuntimeClient = bedrockClient;
    }
    public String invokeNovaPro(String prompt, List<String> base64Image) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode requestBody = createRequestPayload(objectMapper, prompt, base64Image);
            // Convert to JSON string
            String jsonPayload = objectMapper.writeValueAsString(requestBody);
            InvokeModelRequest request = InvokeModelRequest.builder()
                    .modelId("amazon.nova-pro-v1:0")
                    .contentType("application/json")
                    .accept("application/json")
                    .body(SdkBytes.fromUtf8String(jsonPayload))
                    .build();

            InvokeModelResponse response = bedrockRuntimeClient.invokeModel(request);
            String responseJson = response.body().asUtf8String();
            return parseAndDisplayResponse(objectMapper, responseJson);

        } catch (Exception e) {
            System.out.println("Error calling Nova Pro" + e);
            return null;
        }
    }
    private String escapeJson(String input) {
        return input.replace("\"", "\\\"").replace("\n", "\\n");
    }



    private ObjectNode createRequestPayload(ObjectMapper objectMapper, String prompt, List<String> base64Images) {

        ObjectNode requestBody = objectMapper.createObjectNode();

        requestBody.put("schemaVersion", "messages-v1");
        // Create messages array
        ArrayNode messages = objectMapper.createArrayNode();
        ObjectNode message = objectMapper.createObjectNode();
        message.put("role", "user");
        // Create content array
        ArrayNode content = objectMapper.createArrayNode();


        for (String base64Image : base64Images) {
            // Add image content
            ObjectNode imageContent = objectMapper.createObjectNode();
            ObjectNode image = objectMapper.createObjectNode();
            image.put("format", "jpeg");
            ObjectNode source = objectMapper.createObjectNode();
            source.put("bytes", base64Image.trim().replaceAll("\\s+", ""));
            image.set("source", source);
            imageContent.set("image", image);
            content.add(imageContent);
        }
        // Add text content
        ObjectNode textContent = objectMapper.createObjectNode();
        textContent.put("text", "You are an AI assistant trained to extract structured persona information from a multiple images\n" +
                "\n" +
                "From the visible features (face, skin, hair, eyes, accessories, anything in the background etc.), infer only the attributes listed below. Use **only the allowed options** under each field. If something is unclear or cannot be determined from the image, return null or an empty array []. ONLY give output which is visible in the images and don't give any anything else. If something cannot be inferred from images, that output will be blank. If there are multiple people in a photo, follow the person common among all the photos. Give complete JSON in output so that the strcuture is maintained each time. Give ONLY and ONLY JSON in the output.\n" +
                "\n" +
                "Return the result in the following JSON format:\n" +
                "\n" +
                "{\n" +
                "  \"user_id\": \"USER_XXXXX\",\n" +
                "  \"basic_demographics\": {\n" +
                "    \"age\": [\"10s\", \"20s\", \"30s\", \"40s\", \"50s\", \"60s\"],\n" +
                "    \"gender\": [\"Male\", \"Female\", \"Other\"],\n" +
                "    \"location\": (city or null),\n" +
                "    \"occupation\": [\"Student\", \"Professional\", \"Parent\", \"Self-Employed\", \"Other\"],\n" +
                "    \"family_status\": [\"Single\", \"Married\", \"Parent\", \"Joint Family\", \"Other\"],\n" +
                "    \"income_level\": [\"Low\", \"Mid\", \"High\"],\n" +
                "    \"education_level\": [\"School\", \"Graduate\", \"Postgraduate\", \"Doctorate\", \"Other\"],\n" +
                "    \"relationship_status\": [\"Single\", \"In a relationship\", \"Married\", \"Divorced\", \"Other\"],\n" +
                "    \"suggested_colors\":[Suggest any 4 colors which will look good on the user on the basis of his photo. Give Hex code as output in commat separated. Keep the lanaguage in You form like You are telling directly to the user]\n" +
                "  },\n" +
                "  \"facial_features\": {\n" +
                "    \"skin_type\": [\"Oily\", \"Dry\", \"Combination\", \"Normal\", \"Sensitive\", \"Acne-Prone\", \"Mature\"],\n" +
                "    \"skin_tone\": [\"Fair\", \"Light\", \"Medium\", \"Tan\", \"Olive\", \"Brown\", \"Deep\", \"Dark\"],\n" +
                "    \"skin_undertone\": [\"Warm\", \"Cool\", \"Neutral\", \"Olive\", \"Yellow\", \"Pink\", \"Golden\", \"Blue\"],\n" +
                "    \"facial_shape\": [\"Round\", \"Oval\", \"Square\", \"Heart\", \"Diamond\", \"Rectangle\", \"Long\"],\n" +
                "    \"eye_color\": [\"Black\", \"Brown\", \"Blue\", \"Green\", \"Hazel\", \"Grey\", \"Amber\"],\n" +
                "    \"lip_shape\": [\"Thin\", \"Full\", \"Round\", \"Heart\", \"Wide\", \"Cupid's Bow\"],\n" +
                "    \"hair_type\": [\"Straight\", \"Wavy\", \"Curly\", \"Coily\", \"Fine\", \"Thick\", \"Frizzy\"],\n" +
                "    \"hair_length\": [\"Short\", \"Medium\", \"Long\", \"Shoulder-Length\", \"Buzz Cut\", \"Bald\"],\n" +
                "    \"hair_color\": [\"Black\", \"Brown\", \"Blonde\", \"Red\", \"Grey\", \"Salt & Pepper\", \"Dyed\"],\n" +
                "    \"beard_presence\": [true, false],\n" +
                "    \"beard_style\": [\"Clean Shaven\", \"Stubble\", \"Short Beard\", \"Full Beard\", \"Goatee\", \"Circle Beard\", \"Moustache\", \"French Beard\", \"Van Dyke\"],\n" +
                "    \"beard_density\": [\"Sparse\", \"Medium\", \"Dense\", \"Patchy\"],\n" +
                "    \"beard_color\": [\"Black\", \"Brown\", \"Grey\", \"Salt & Pepper\", \"Dyed\"],\n" +
                "    \"skin_tone_description\"\": [On the basis of user skin tone, give a small description/paragraph for what looks good on the user and how their skinn tone is]\n" +
                "  },\n" +
                "  \"emotional_state_and_personality_traits\": {\n" +
                "    \"mood\": [\"Neutral\", \"Happy\", \"Energetic\", \"Calm\", \"Sad\", \"Stressed\"],\n" +
                "    \"color_tone\": [\"Bright\", \"Muted\", \"Dark\", \"Neutral\"],\n" +
                "    \"energy\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"personality_type\": [\"Extrovert\", \"Introvert\", \"Ambivert\"],\n" +
                "    \"confidence_level\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"introvert_extrovert\": [\"Introvert\", \"Extrovert\", \"Ambivert\"]\n" +
                "  },\n" +
                "  \"lifestyle_and_interests\": {\n" +
                "    \"hobbies\": [\"Travel\", \"Fitness\", \"Photography\", \"Reading\", \"Cooking\", \"Music\", \"Gaming\", \"Gardening\"],\n" +
                "    \"pets\": [\"Dog\", \"Cat\", \"Bird\", \"Fish\", \"No Pets\"],\n" +
                "    \"parenting\": [true, false],\n" +
                "    \"food_preferences\": [\"Vegetarian\", \"Vegan\", \"Non-Vegetarian\", \"Pescatarian\", \"Keto\", \"Gluten-Free\"],\n" +
                "    \"exercise_routine\": [\"Daily\", \"Weekly\", \"Occasionally\", \"Never\"],\n" +
                "    \"outdoor_activities\": [\"Running\", \"Hiking\", \"Cycling\", \"Yoga\", \"None\"],\n" +
                "    \"social_media_usage\": [\"Instagram\", \"TikTok\", \"YouTube\", \"Snapchat\", \"Facebook\", \"LinkedIn\"]\n" +
                "  },\n" +
                "  \"accessories_preferences\": {\n" +
                "    \"jewelry\": [\"Earrings\", \"Necklaces\", \"Bracelets\", \"Rings\", \"Nose Pins\", \"None\"],\n" +
                "    \"eyewear\": [\"Sunglasses\", \"Prescription Glasses\", \"Blue Light Glasses\", \"None\"],\n" +
                "    \"bags\": [\"Backpacks\", \"Handbags\", \"Crossbody Bags\", \"Clutches\", \"None\"],\n" +
                "    \"footwear\": [\"Sneakers\", \"Flats\", \"Heels\", \"Boots\", \"Loafers\", \"Sandals\"],\n" +
                "    \"hats_and_caps\": [\"Baseball Caps\", \"Beanies\", \"Bucket Hats\", \"Sun Hats\", \"None\"],\n" +
                "    \"scarves_and_wraps\": [\"Woolen Scarves\", \"Silk Scarves\", \"Stoles\", \"Headbands\", \"None\"]\n" +
                "  },\n" +
                "  \"apparel_preferences\": {\n" +
                "    \"style\": [\"Casual\", \"Streetwear\", \"Formal\", \"Minimalist\", \"Athleisure\", \"Boho\", \"Vintage\"],\n" +
                "    \"color\": [\"Black\", \"White\", \"Beige\", \"Pastel\", \"Bright\", \"Neutral\"],\n" +
                "    \"fabric\": [\"Cotton\", \"Linen\", \"Denim\", \"Silk\", \"Polyester\", \"Blends\"],\n" +
                "    \"fit\": [\"Regular\", \"Slim\", \"Loose\", \"Oversized\"],\n" +
                "    \"pattern_preference\": [\"Solid\", \"Stripes\", \"Floral\", \"Geometric\", \"Plaid\"],\n" +
                "    \"sleeve_length\": [\"Short Sleeve\", \"Long Sleeve\", \"Sleeveless\"],\n" +
                "    \"neck_type\": [\"Round Neck\", \"V-Neck\", \"Collared\", \"Turtleneck\"],\n" +
                "    \"waist_type\": [\"High-Waist\", \"Mid-Rise\", \"Low-Rise\"]\n" +
                "  },\n" +
                "  \"brand_affinity\": {\n" +
                "    \"logos\": [\"Nike\", \"Zara\", \"Adidas\", \"H&M\", \"Gucci\", \"None\"],\n" +
                "    \"patterns\": [\"Minimal\", \"Street\", \"Luxury\", \"Trendy\"],\n" +
                "    \"brand_loyalty\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"designer_vs_fast_fashion\": [\"Designer\", \"Fast Fashion\", \"Both\"],\n" +
                "    \"ethical_preferences\": [\"Sustainable\", \"Recycled\", \"None\"],\n" +
                "    \"local_vs_global\": [\"Local\", \"Global\", \"Both\"]\n" +
                "  },\n" +
                "  \"occasion_and_context\": {\n" +
                "    \"event_type\": [\"Work\", \"Party\", \"Casual Outing\", \"Gym\", \"Wedding\", \"Festive\"],\n" +
                "    \"activity\": [\"Travel\", \"Shopping\", \"Dining\", \"Gym\", \"Photography\"],\n" +
                "    \"setting\": [\"Indoor\", \"Outdoor\", \"Urban\", \"Rural\"],\n" +
                "    \"frequency\": [\"Daily\", \"Weekly\", \"Occasional\"],\n" +
                "    \"group_size\": [\"Solo\", \"Couple\", \"Small Group\", \"Large Group\"],\n" +
                "    \"location_type\": [\"City\", \"Nature\", \"Home\", \"Office\"]\n" +
                "  },\n" +
                "  \"seasonal_and_weather_preferences\": {\n" +
                "    \"cold_weather\": [true, false],\n" +
                "    \"hot_weather\": [true, false],\n" +
                "    \"layering\": [true, false],\n" +
                "    \"accessories\": [\"Hats\", \"Sunglasses\", \"Scarves\", \"Gloves\", \"None\"],\n" +
                "    \"weather_sensitivity\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"preferred_seasons\": [\"Summer\", \"Winter\", \"Monsoon\", \"Spring\", \"Autumn\"]\n" +
                "  },\n" +
                "  \"budget_sensitivity\": {\n" +
                "    \"price_range\": [\"Budget\", \"Mid-Range\", \"Premium\", \"Luxury\"],\n" +
                "    \"purchase_frequency\": [\"Frequent\", \"Monthly\", \"Occasional\"],\n" +
                "    \"discount_sensitivity\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"bulk_buying\": [true, false],\n" +
                "    \"price_elasticity\": [\"High\", \"Medium\", \"Low\"]\n" +
                "  },\n" +
                "  \"fashion_risk_appetite\": {\n" +
                "    \"style_boldness\": [\"Minimalist\", \"Maximalist\", \"Moderate\"],\n" +
                "    \"pattern_complexity\": [\"Simple\", \"Busy\", \"Moderate\"],\n" +
                "    \"experimentation_level\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"color_preference\": [\"Bright\", \"Neutral\", \"Muted\", \"Dark\"],\n" +
                "    \"trend_adoption\": [\"Early Adopter\", \"Mainstream\", \"Late Adopter\"]\n" +
                "  },\n" +
                "  \"cultural_and_regional_influences\": {\n" +
                "    \"ethnic_wear\": [\"Yes\", \"No\"],\n" +
                "    \"traditional_vs_western\": [\"Traditional\", \"Western\", \"Fusion\"],\n" +
                "    \"regional_fashion\": [\"North Indian\", \"South Indian\", \"East Indian\", \"West Indian\", \"None\"],\n" +
                "    \"festive_dressing\": [\"Yes\", \"No\"],\n" +
                "    \"local_trends\": [\"Regional\", \"National\", \"Global\"]\n" +
                "  },\n" +
                "  \"purchase_intent_and_frequency\": {\n" +
                "    \"purchase_type\": [\"Impulse\", \"Planned\"],\n" +
                "    \"buying_behavior\": [\"Practical\", \"Emotional\", \"Trend-Driven\"],\n" +
                "    \"gifting_tendencies\": [true, false],\n" +
                "    \"impulse_control\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"need_vs_want\": [\"Need\", \"Want\", \"Both\"]\n" +
                "  },\n" +
                "  \"social_influence_and_peer_groups\": {\n" +
                "    \"group_size\": [\"Solo\", \"Small Group\", \"Large Group\"],\n" +
                "    \"hashtags\": [\"#OOTD\", \"#StyleInspo\", \"#FitnessGoals\", \"#TravelDiaries\", \"None\"],\n" +
                "    \"trends\": [\"Streetwear\", \"Smart Casual\", \"Traditional\", \"Minimalist\", \"None\"],\n" +
                "    \"influencer_follow\": [true, false],\n" +
                "    \"peer_pressure\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"social_proof\": [\"High\", \"Medium\", \"Low\"]\n" +
                "  },\n" +
                "  \"digital_footprint_and_context\": {\n" +
                "    \"social_media_presence\": [\"Instagram\", \"TikTok\", \"Facebook\", \"YouTube\", \"None\"],\n" +
                "    \"hashtags\": [\"#Fashion\", \"#Travel\", \"#Fitness\", \"#Beauty\", \"None\"],\n" +
                "    \"engagement_levels\": [\"High\", \"Medium\", \"Low\"],\n" +
                "    \"online_persona\": [\"Fashion Enthusiast\", \"Fitness Buff\", \"Traveler\", \"Minimalist\", \"None\"],\n" +
                "    \"platform_preference\": [\"Instagram\", \"YouTube\", \"TikTok\", \"None\"]\n" +
                "  },\n" +
                "  \"life_events_and_transitions\": {\n" +
                "    \"milestones\": [\"Graduation\", \"New Job\", \"Marriage\", \"None\"],\n" +
                "    \"major_life_changes\": [\"Relocation\", \"Career Shift\", \"Fitness Journey\", \"None\"],\n" +
                "    \"personal_growth\": [\"Health\", \"Wealth\", \"Skills\", \"None\"],\n" +
                "    \"long_term_goals\": [\"Success\", \"Stability\", \"Creativity\", \"None\"]\n" +
                "  }\n" +
                "}\n");
        content.add(textContent);
        message.set("content", content);
        messages.add(message);
        requestBody.set("messages", messages);
        // Add inference configuration

        ObjectNode inferenceConfig = objectMapper.createObjectNode();
        inferenceConfig.put("max_new_tokens", 5000);
        inferenceConfig.put("temperature", 0.4);
        inferenceConfig.put("top_p", 0.3);
        inferenceConfig.put("top_k", 50);
        requestBody.set("inferenceConfig", inferenceConfig);
        return requestBody;
    }
    private String parseAndDisplayResponse(ObjectMapper objectMapper, String responseJson) {

        try {
            ObjectNode responseNode = (ObjectNode) objectMapper.readTree(responseJson);
            // Navigate to the response text

            String modelResponse = responseNode
                    .path("output")
                    .path("message")
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();
            System.out.println("\nModel Response:");
            String cleanedJson = modelResponse
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            return cleanedJson;
        } catch (Exception e) {
            System.err.println("Error parsing response: " + e.getMessage());
            System.out.println("Raw response: " + responseJson);
        }
        return null;
    }

}

