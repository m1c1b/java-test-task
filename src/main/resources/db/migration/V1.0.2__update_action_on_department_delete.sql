ALTER TABLE t_users
    DROP CONSTRAINT fk6rmluaq2cbbxwmgxlcqb6ugik;

ALTER TABLE t_users
    ADD CONSTRAINT fk6rmluaq2cbbxwmgxlcqb6ugik
        FOREIGN KEY (department_id) REFERENCES t_departments
            ON DELETE SET NULL;