package tw.harry.springboot.spring01.apis;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tw.harry.springboot.spring01.dto.Gift;
import tw.harry.springboot.spring01.dto.Member;
import tw.harry.springboot.spring01.utils.User;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/harry06")
public class Harry06 {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @RequestMapping("/test1")
    public String test1() {
        jdbc.update("DELETE FROM gifts", new HashMap<>());
        jdbc.update("ALTER TABLE gifts AUTO_INCREMENT = 1", new HashMap<>());

        String url = "https://data.moa.gov.tw/Service/OpenData/ODwsv/ODwsvAgriculturalProduce.aspx";
        String json = restTemplate.getForObject(url, String.class);
        List<Gift> gifts = mapper.readValue(json, new TypeReference<>() {});

        System.out.println(gifts.size());
        System.out.println(gifts.get(0).getName());

        String sql = """
            INSERT INTO gifts
            (name, addr, tel)
            VALUES
            (:name, :addr, :tel)
        """;

        MapSqlParameterSource[] params = new MapSqlParameterSource[gifts.size()];
        for (int i = 0; i < gifts.size(); i++) {
            Gift gift = gifts.get(i);
            params[i] = new MapSqlParameterSource();
            params[i].addValue("name", gift.getName());
            params[i].addValue("addr", gift.getAddr());
            params[i].addValue("tel", gift.getTel());
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int[] rows = jdbc.batchUpdate(sql, params, keyHolder);
        System.out.println(Arrays.toString(rows));

        return json;
    }
}
