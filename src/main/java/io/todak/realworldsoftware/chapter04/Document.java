package io.todak.realworldsoftware.chapter04;

import java.util.Map;

public class Document {

    private final Map<String, String> attributes;

    Document(final Map<String, String> attributes) {
        this.attributes = attributes;
        //TODO: 생성자가 Public이 아닌경우는 추후에 조사해서 적어둘 것.
        //해당 패키지 내에서만 사용할 수 있도록 access level을 default 설정.
    }

    public String getAttribute(final String attributeName) {
        return attributes.get(attributeName);
    }

}
