package com.example.demo.service;

import com.example.demo.dto.NewsInfoDTO;
import com.example.demo.entity.NewsInfo;
import com.example.demo.repository.APIRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class APIService {

    @Autowired
    APIRepository apiRepository;

    public List<Map<String, Object>> getSearchContent() {
        String clientId = "fGCoGNkQJGC4pOK7MQhP"; //애플리케이션 클라이언트 아이디
        String clientSecret = "kLHKsWK6cv"; //애플리케이션 클라이언트 시크릿


        String text = null;
        HttpURLConnection con = null;
        String result = "";
        List<Map<String, Object>> items = null;
        try {
            text = URLEncoder.encode("IT", StandardCharsets.UTF_8);
            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text + "&display=100&sort=date";    // JSON 결과
            //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // XML 결과

            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                result = readBody(con.getInputStream());
                String[] fields = {"title", "pubDate", "link", "description"};
                Map<String, Object> jsonResult = this.getResult(result, fields);

                if (jsonResult.size() > 0) {
                    System.out.println("total -> " + jsonResult.get("total"));
                }

                String tag_pattern = "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>"; // [<p> 등 태그 제거]
                Pattern num_reg_entity_pattern = Pattern.compile("&#[0-9]+;"); // [&#09; 형태 제거]
                Pattern char_reg_entity_pattern = Pattern.compile("&[a-zA-Z]+;"); // [&amp; 형태 제거]
                Pattern char_normal_entity_pattern = Pattern.compile(" [a-zA-Z]+;"); // [amp; 형태 제거]

                items = (List<Map<String, Object>>) jsonResult.get("result");

                for (Map<String, Object> item : items) {
                    System.out.println("======================================");
                    for (String field : fields) {

                        String replPatt = "";
                        replPatt = ((String) item.get(field)).replaceAll(tag_pattern, " ");

                        if(field == "pubDate"){
//                            replPatt = replPatt.replace(",","");
//                            replPatt = replPatt.replace("+0900","GMT+0900");
//                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                            System.out.println("@@@@@");
//                            System.out.println(replPatt);
//                            Date date = formatter.parse(replPatt);
//                            replPatt = String.valueOf(date);
//                            System.out.println("@@@@@");
//                            System.out.println(replPatt);
//                            replPatt = replPatt.replace(",","").replace(" +0900","");
                            System.out.println("@@@@@");
                            System.out.println(new Date(replPatt));
                            SimpleDateFormat recvSimpleFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                            // 여기에 원하는 포맷을 넣어주면 된다
                            SimpleDateFormat tranSimpleFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            Date data = recvSimpleFormat.parse(String.valueOf(new Date(replPatt)));
                            replPatt = tranSimpleFormat.format(data);

//                            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
//                            Date date = formatter.parse(String.valueOf(new Date(replPatt)));
//                            System.out.println(date);
//                            replPatt = String.valueOf(date);
                        }

                        // [html 태그 2차 제거 실시]
                        Matcher num_reg_mat = num_reg_entity_pattern.matcher(replPatt);
                        replPatt = num_reg_mat.replaceAll("");

                        Matcher char_reg_mat = char_reg_entity_pattern.matcher(replPatt);
                        replPatt = char_reg_mat.replaceAll("");

                        Matcher char_normal_mat = char_normal_entity_pattern.matcher(replPatt);
                        replPatt = char_normal_mat.replaceAll("");

                        // [html 태그 연속 공백 제거 실시]
                        replPatt = replPatt.replaceAll("\\s+", " ");
                        item.replace(field, replPatt);

//                        System.out.println(field + " -> " + item.get(field));
                        System.out.println(field + " -> " + replPatt);
                    }
                    long cnt = apiRepository.findNews((String)item.get("title"));
                    System.out.println("왜 안되는지 아시는분");
                    System.out.println(cnt);
                    if(cnt == 0){
                        apiRepository.insertNews(convertMapToEntity(item));
                    }
                }


            } else {
                result = readBody(con.getErrorStream());
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }
//        catch (java.text.ParseException e) {
//            throw new RuntimeException(e);
//        }


//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", clientId);
//        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
//        String responseBody = get(apiURL,requestHeaders);

        return items;
    }

    public NewsInfo convertMapToEntity(Map<String, Object> item) {
        NewsInfo newsInfo = NewsInfo.builder()
                .title((String) item.get("title"))
                .pubDate((String) item.get("pubDate"))
                .link((String) item.get("link"))
                .description((String) item.get("description"))
                .build();
        System.out.println(newsInfo);
        return newsInfo;
    }

    private Map<String, Object> getResult(String result, String[] fields) {
        Map<String, Object> obj = new HashMap<>();


        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);

            obj.put("total", jsonObject.get("total"));

            JSONArray items = (JSONArray) jsonObject.get("items");

            List<Map<String, Object>> itemList = new ArrayList<>();


            for(int i = 0; i < items.size(); i++){
                JSONObject item = (JSONObject) items.get(i);
                Map<String, Object> itemMap = new HashMap<>();
                for(String field : fields){
                    itemMap.put(field, item.get(field));
                }
                itemList.add(itemMap);
            }

            obj.put("result", itemList);

        } catch (ParseException e) {
            System.out.println("파싱 실패!" + e.getMessage());
//            throw new RuntimeException(e);
        }
        return obj;
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;

            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    public List<NewsInfo> getNewsList() {
        List<NewsInfo> newsInfoList = apiRepository.getNewsList();

        return ;
    }

    public NewsInfo dtoToEntity(NewsInfoDTO newsInfoDTO){
        return NewsInfo.builder()
                .seq(newsInfoDTO.getSeq())
                .title(newsInfoDTO.getTitle())
                .pubDate(newsInfoDTO.getPubDate())
                .views(newsInfoDTO.getViews())
                .description(newsInfoDTO.getDescription())
                .link(newsInfoDTO.getLink())
                .build();
    }

    public NewsInfoDTO entityToDTO(NewsInfo newsInfo){
        return NewsInfoDTO.builder()
                .seq(newsInfo.getSeq())
                .title(newsInfo.getTitle())
                .pubDate(newsInfo.getPubDate())
                .views(newsInfo.getViews())
                .description(newsInfo.getDescription())
                .link(newsInfo.getLink())
                .build();
    }
}
