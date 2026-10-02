package tw.harry.springboot.spring01.apis;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tw.harry.springboot.spring01.dto.Gift;
import tw.harry.springboot.spring01.utils.GiftRowMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/harry07")
public class Harry07 {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private GiftRowMapper giftRowMapper;

    @GetMapping("/")
    public List<Gift> test1() {
        String sql = "SELECT id, name, addr, tel FROM gifts";
        return jdbc.query(sql, giftRowMapper);
    }

    @GetMapping("/{id}")
    public Gift findById(@PathVariable int id) {
        String sql = """
            SELECT id, name, addr, tel
            FROM gifts
            WHERE id = :id
        """;

        HashMap<String, Integer> args = new HashMap<>();
        args.put("id", id);

        try {
            Gift gift = jdbc.queryForObject(sql, args, giftRowMapper);
            return gift;
        } catch (Exception e) {
            e.printStackTrace();
            Gift gift = new Gift();
            gift.setId(id);
            gift.setError(-1);
            return gift;
        }
    }

    @GetMapping("/v2/{id}")
    public Gift findByIdV2(@PathVariable int id) {
        String sql = """
            SELECT id, name, addr, tel
            FROM gifts
            WHERE id = :id
        """;

        HashMap<String, Integer> args = new HashMap<>();
        args.put("id", id);

        List<Gift> gifts = jdbc.query(sql, args, giftRowMapper);
        if (gifts.isEmpty()) {
            Gift gift = new Gift();
            gift.setId(id);
            gift.setError(-1);
            return gift;
        } else {
            return gifts.get(0);
        }
    }

    @GetMapping("/search")
    public List<Gift> findByKeywordParam(@RequestParam String keyword) {
        String sql = """
            SELECT id, name, addr, tel
            FROM gifts
            WHERE name LIKE :keyword
                  OR addr LIKE :keyword
                  OR tel  LIKE :keyword
        """;

        HashMap<String, String> args = new HashMap<>();
        args.put("keyword", "%" + keyword + "%");

        return jdbc.query(sql, args, giftRowMapper);
    }

    @GetMapping("/search/{keyword}")
    public List<Gift> findByKeywordPath(@PathVariable String keyword) {
        String sql = """
            SELECT id, name, addr, tel
            FROM gifts
            WHERE name LIKE :keyword
                  OR addr LIKE :keyword
                  OR tel  LIKE :keyword
        """;

        HashMap<String, String> args = new HashMap<>();
        args.put("keyword", "%" + keyword + "%");

        return jdbc.query(sql, args, giftRowMapper);
    }
}
