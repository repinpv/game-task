package com.demo.gametask.layout;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.Writer;

public class Layout implements Mustache.Lambda {

    String body;

    @Override
    public void execute(Template.Fragment frag, Writer out) throws IOException {
        body = frag.execute();
    }
}
