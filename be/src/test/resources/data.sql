INSERT INTO member (type, email, nickname, profile_img_url)
VALUE ('KAKAO', 'nag@codesquad.kr', 'nag', 'nagProfile');

INSERT INTO member_address (is_last_visited, address_id, member_id)
VALUES (1, 1, 1),
       (0, 10, 1);
