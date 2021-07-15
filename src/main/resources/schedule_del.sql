DELETE FROM rest_posts WHERE last_request_date + interval '5' day < CURRENT_DATE
-- SELECT * FROM rest_posts WHERE last_request_date + interval '5' day < CURRENT_DATE