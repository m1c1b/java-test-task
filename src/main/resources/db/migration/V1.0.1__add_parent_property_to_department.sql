ALTER TABLE IF EXISTS t_departments
    ADD COLUMN parent_department_id int4;
ALTER TABLE IF EXISTS t_departments
    ADD CONSTRAINT fkk2jueelcajxh7kw16k4kpaedr FOREIGN KEY (parent_department_id) REFERENCES t_departments;