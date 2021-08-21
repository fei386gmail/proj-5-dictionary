package com.example.dictionary;

import io.xjar.XCryptos;

public class crypt {
    public static void main(String[] args) throws Exception {
        crypt.produce();
    }

    public static void produce() throws Exception {
        XCryptos.encryption()
                .from("/Users/chenfei/OneDrive/IDEAProject/proj 5 dictionary/target/dictionary-0.0.1-SNAPSHOT.jar")
                .use("io.xjar")
                .include("/**/*.class")
                .include("/**/*.xml")
                .include("/**/*.yml")
                .include("/**/*.html")
                .to("/Users/chenfei/OneDrive/IDEAProject/proj 5 dictionary/target/dictionary-0.0.1-crypted.jar");
    }
}
