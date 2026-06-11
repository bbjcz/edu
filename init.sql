create table student (
    id serial primary key,
    name text not null
);

create table teacher (
    id serial primary key,
    name text not null
);

create table users (
    id serial primary key,
    password text not null,
    role text not null,
    student_id int unique,
    teacher_id int unique,
    foreign key (student_id) references student(id),
    foreign key (teacher_id) references teacher(id),
    constraint st_check check (
        (role = 'student' and student_id is not null and teacher_id is null) or
        (role = 'teacher' and teacher_id is not null and student_id is null) or
        (role = 'admin' and student_id is null and teacher_id is null)
    )
);

create view user_info as
select users.id uid, student.id, student.name, role
from users join student on student_id = student.id
where role = 'student'
union all
select users.id, teacher.id, teacher.name, role
from users join teacher on teacher_id = teacher.id
where role = 'teacher'
union all
select users.id, null, null, role
from users where role = 'admin';

create table course (
    id serial primary key,
    name text not null,
    point int not null
        check (point > 0),
);

create table class (
    id serial primary key,
    course_id int not null,
    teacher_id int not null,
    foreign key (course_id) references course(id),
    foreign key (teacher_id) references teacher(id)
);

create table enrollment (
    student_id int not null,
    class_id int not null,
    score int
        check (score >= 0 and score <= 100 or score is null),
    primary key (student_id, class_id),
    foreign key (student_id) references student(id),
    foreign key (class_id) references class(id)
);

create table score_update_log (
    id serial primary key,
    student_id int not null,
    class_id int not null,
    new_score int,
    update_time timestamp default current_timestamp,
    foreign key (student_id) references student(id),
    foreign key (class_id) references class(id)
);

create function update_score()
returns trigger as $$
begin
    insert into score_update_log (student_id, class_id, new_score)
    values (NEW.student_id, NEW.class_id, NEW.score);
    return NEW;
end;
$$ language plpgsql;

create trigger score_update_trigger
after update of score on enrollment
for each row
execute procedure update_score();

create view student_score as
select users.id uid, class.id class_id, course.name course_name,
teacher.name teacher_name, course.point, enrollment.score
from class join enrollment on enrollment.class_id = class.id
join course on class.course_id = course.id
join teacher on class.teacher_id = teacher.id
join users on users.student_id = enrollment.student_id
where users.role = 'student';

create view student_average_score as
select users.id uid, round(score_sum / point_sum, 1) as average_score
from
(
    select student_id, sum(score * course.point) as score_sum
    from enrollment join class on enrollment.class_id = class.id
    join course on class.course_id = course.id
    group by student_id
) s join
(
    select student_id, sum(course.point) as point_sum
    from enrollment join class on enrollment.class_id = class.id
    join course on class.course_id = course.id
    where score is not null
    group by student_id
) a on s.student_id = a.student_id
join users on users.student_id = s.student_id
where users.role = 'student';

create view teacher_classes as
select users.id uid, class.id class_id, course.name course_name
from class join users on users.teacher_id = class.teacher_id
join course on class.course_id = course.id
where users.role = 'teacher';

create view classes_score as
select users.id uid, class.id class_id, enrollment.student_id,
student.name student_name, enrollment.score
from enrollment join class on enrollment.class_id = class.id
join student on enrollment.student_id = student.id
join users on users.teacher_id = class.teacher_id
where users.role = 'teacher';

create view classes_info as
select uid, s.class_id, course_name, average_score
from
(
    select class.id class_id, round(avg(score), 1) average_score
    from enrollment join class on enrollment.class_id = class.id
    group by class.id
) s join
(
    select users.id uid, class.id class_id, course.name course_name
    from class join course on class.course_id = course.id
    join users on users.teacher_id = class.teacher_id
    where users.role = 'teacher'
) c on s.class_id = c.class_id;

create view student_unenrolled_classes as
select users.id uid, class.id class_id
from class cross join student
join users on users.student_id = student.id
where course_id not in (
    select course_id from class
    join enrollment on enrollment.class_id = class.id
    where student_id = student.id
) and users.role = 'student';
