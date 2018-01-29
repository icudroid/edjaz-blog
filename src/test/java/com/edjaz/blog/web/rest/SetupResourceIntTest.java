package com.edjaz.blog.web.rest;


import com.edjaz.blog.BlogApp;
import com.edjaz.blog.domain.Blog;
import com.edjaz.blog.repository.BlogRepository;
import com.edjaz.blog.repository.search.BlogSearchRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static com.edjaz.blog.web.rest.BlogResourceIntTest.createEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
@WithMockUser
public class SetupResourceIntTest {

    @Autowired
    private EntityManager em;

    private MockMvc restSetupMockMvc;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private BlogSearchRepository blogSearchRepository;


    @Autowired
    private WebApplicationContext context;

    private Blog blog;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SetupResource blogItemResource = new SetupResource();
        this.restSetupMockMvc = MockMvcBuilders.webAppContextSetup(context)
        .build();
    }

    @Before
    public void initTest() {
        blogSearchRepository.deleteAll();
        blog = createEntity(em);
    }

    @Test
    @WithAnonymousUser
    public void should_return_true_when_no_blog_and_has_anonymous() throws Exception {
        restSetupMockMvc.perform(get("/api/setup"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }


    @Test
    @WithAnonymousUser
    public void should_return_forbiden_when_has_blog_has_anonymous() throws Exception {
        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup"))
            .andExpect(status().isForbidden());
    }



    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void should_return_true_when_has_blog_has_admin() throws Exception {
        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }


    @Test
    @WithMockUser(username="user",roles={"USER"})
    public void should_return_forbiden_when_has_blog_has_admin() throws Exception {
        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup"))
            .andExpect(status().isForbidden());
    }


}
