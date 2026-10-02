package tw.harry.springboot.spring01.apis;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import tw.harry.springboot.spring01.dto.Member;
import tw.harry.springboot.spring01.dto.MemberResponse;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/harry05")
public class Harry05 {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    @Autowired
    private MemberResponse memberResponse;

    @RequestMapping("/test1")
    public void test1() {
        String sql = """
            INSERT INTO cust
            (cname, tel, birthday)
            VALUES
            (:cname, :tel, :birthday)
        """;

        HashMap<String, String> args = new HashMap<>();
        args.put("cname", "joyce");
        args.put("tel", "188-555-1212");
        args.put("birthday", "1991-05-05");

        int n = jdbc.update(sql, args);
        System.out.println(n);
    }

    @PostMapping("/test2")
    public void test2(@RequestBody Member member) {
        String sql = """
            INSERT INTO member
            (account, passwd, name)
            VALUES
            (:account, :passwd, :name)
        """;

        HashMap<String, String> args = new HashMap<>();
        args.put("account", member.getAccount());
        args.put("passwd", BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
        args.put("name", member.getName());

        int n = jdbc.update(sql, args);
        System.out.println(n);
    }

    @PostMapping(value = { "", "/{isGetId}" })
    public MemberResponse test3(@RequestBody Member member, @PathVariable(required = false) Boolean isGetId) {
        String sql = """
            INSERT INTO member
            (account, passwd, name)
            VALUES
            (:account, :passwd, :name)
        """;

        HashMap<String, String> args = new HashMap<>();
        args.put("account", member.getAccount());
        args.put("passwd", BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
        args.put("name", member.getName());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int n = jdbc.update(sql, new MapSqlParameterSource(args), keyHolder);
        System.out.println(n);
        if (n > 0) {
            System.out.printf("%d\n", keyHolder.getKey().intValue());

            if (isGetId == null) isGetId = false;

            if (isGetId) {
                member.setId(keyHolder.getKey().intValue());
            } else {
                member.setId(null);
            }

            member.setPasswd("xxxxx");

            memberResponse.setError(0);
            memberResponse.setMsg("Insert Success");
            memberResponse.setMember(member);

            return memberResponse;
        } else {
            memberResponse.setError(-1);
            memberResponse.setMsg("Insert Failed, count=0");
            memberResponse.setMember(null);

            return memberResponse;
        }
    }

    @PostMapping("/multidata/{isGetId}")
    public MemberResponse test4(
        @RequestBody List<Member> members,
        @PathVariable(required = false, name = "isGetId") Boolean isGetId
    ) {

        String sql = """
            INSERT INTO member
            (account, passwd, name)
            VALUES
            (:acc, :pass, :name)
        """;

        MapSqlParameterSource[] params = new MapSqlParameterSource[members.size()];
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
            params[i] = new MapSqlParameterSource();
            params[i].addValue("acc", member.getAccount());
            params[i].addValue("pass", BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
            params[i].addValue("name", member.getName());
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int[] rows = jdbc.batchUpdate(sql, params, keyHolder);
        int totalRows = 0;

        if (isGetId == null) isGetId = false;
        for (int i = 0; i < members.size(); i++) {
            totalRows += rows[i];

            Member member = members.get(i);
            member.setPasswd("xxxxx");
            if (isGetId) {
                members.get(i).setId(((BigInteger) keyHolder.getKeyList().get(i).get("GENERATED_KEY")).intValue());
            }
        }

        if (totalRows > 0) {

            memberResponse.setError(0);
            memberResponse.setMsg("Insert Success");
            memberResponse.setMembers(members);

            return memberResponse;
        } else {
            memberResponse.setError(-1);
            memberResponse.setMsg("Insert Failed, count=0");
            memberResponse.setMembers(null);

            return memberResponse;
        }
    }

    @PostMapping("/test5")
    public MemberResponse test5(
        @RequestBody List<Member> members,
        @PathVariable(required = false) Boolean isGetId
    ) {
        for (Member member : members) {
            test3(member, isGetId);
        }
        memberResponse.setError(0);
        memberResponse.setMsg("Insert Success");
        memberResponse.setMembers(members);
        return memberResponse;
    }

    @DeleteMapping("/{id}")
    public void test6(@PathVariable Integer id) {
        String sql = """
            DELETE FROM member
            WHERE id = :id
        """;

        HashMap<String, Integer> args = new HashMap<>();
        args.put("id", id);
        jdbc.update(sql, args);
    }

    @PutMapping("")
    public void test7(@RequestBody Member member) {
        String sql = """
            UPDATE member
            SET name = :name
            WHERE id = :id
        """;
        HashMap<String, String> args = new HashMap<>();
        args.put("name", member.getName());
        args.put("id", String.valueOf(member.getId()));
        jdbc.update(sql, args);
    }
}
