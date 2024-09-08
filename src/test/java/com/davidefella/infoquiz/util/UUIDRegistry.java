package com.davidefella.infoquiz.util;

import java.util.UUID;

/*
 * DATA TEST:
 *
 * a --> student
 * b --> teacher
 * c --> evaluation
 * d --> questions
 * e --> answer
 * */
public class UUIDRegistry {

    public static final UUID STUDENT_1 = UUID.fromString("a1000000-e89b-12d3-a456-426614174000");

    public static final UUID TEACHER_1 = UUID.fromString("a2000000-e89b-12d3-a456-426614174000");
    public static final UUID TEACHER_2 = UUID.fromString("a3000000-e89b-12d3-a456-426614174000");


    public static final UUID EVALUATION_1 = UUID.fromString("c1000000-e89b-12d3-a456-426614174000");
    public static final UUID EVALUATION_2 = UUID.fromString("c2000000-e89b-12d3-a456-426614174001");

    public static final UUID QUESTION_1 = UUID.fromString("d1000000-e89b-12d3-a456-426614174002");
    public static final UUID QUESTION_2 = UUID.fromString("d2000000-e89b-12d3-a456-426614174003");
    public static final UUID QUESTION_3 = UUID.fromString("d3000000-e89b-12d3-a456-426614174004");

    public static final UUID ANSWER_1_Q1 = UUID.fromString("b0000004-e89b-12d3-a456-426614174005");
    public static final UUID ANSWER_2_Q1 = UUID.fromString("b0000005-e89b-12d3-a456-426614174006");
    public static final UUID ANSWER_3_Q1 = UUID.fromString("b0000006-e89b-12d3-a456-426614174007");

    public static final UUID ANSWER_1_Q2 = UUID.fromString("b0000007-e89b-12d3-a456-426614174008");
    public static final UUID ANSWER_2_Q2 = UUID.fromString("b0000008-e89b-12d3-a456-426614174009");
    public static final UUID ANSWER_3_Q2 = UUID.fromString("b0000009-e89b-12d3-a456-42661417400a");

    public static final UUID ANSWER_1_Q3 = UUID.fromString("b0000009-e89b-12d3-a456-42661417400b");
    public static final UUID ANSWER_2_Q3 = UUID.fromString("b0000010-e89b-12d3-a456-42661417400c");
    public static final UUID ANSWER_3_Q3 = UUID.fromString("b0000011-e89b-12d3-a456-42661417400d");
}
