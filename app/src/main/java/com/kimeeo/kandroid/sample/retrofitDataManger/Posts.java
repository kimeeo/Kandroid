package com.kimeeo.kandroid.sample.retrofitDataManger;

import java.util.List;

/**
 * Created by bpa001 on 4/16/16.
 */
public class Posts {

    public List<Post> posts;


    public class Post {

        private String id;
        private String category_id;
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
