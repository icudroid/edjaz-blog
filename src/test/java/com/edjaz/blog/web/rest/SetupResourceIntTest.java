package com.edjaz.blog.web.rest;


import com.edjaz.blog.BlogApp;
import com.edjaz.blog.domain.Blog;
import com.edjaz.blog.domain.User;
import com.edjaz.blog.repository.BlogRepository;
import com.edjaz.blog.repository.UserRepository;
import com.edjaz.blog.repository.search.BlogSearchRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static com.edjaz.blog.web.rest.BlogResourceIntTest.createEntity;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
@WithMockUser
public class SetupResourceIntTest {

    @Autowired
    private EntityManager em;

    private MockMvc restSetupMockMvc;

    @Autowired
    private UserRepository userRepository;

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
        this.restSetupMockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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

    private static final String DEFAULT_ADMIN_USER_LOGIN = "admin";
    private static final String DEFAULT_LANGKEY = "en";
    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    private static final String DEFAULT_EMAIL = "johndoe@localhost";


    public User createDefaultAdminUser() {
        User user = new User();
        user.setLogin(DEFAULT_ADMIN_USER_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(RandomStringUtils.randomAlphabetic(5) + DEFAULT_EMAIL);
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        return user;
    }

    @Test
    @WithAnonymousUser
    public void should_return_id_admin_when_no_blog_and_has_anonymous() throws Exception {
        Long id = userRepository.findOneByLogin("admin")
            .orElseThrow(() -> new UsernameNotFoundException("User admin not found"))
            .getId();
        restSetupMockMvc.perform(get("/api/setup/user"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(id.toString()))
        ;
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
    @WithAnonymousUser
    public void should_id_admin_return_forbiden_when_has_blog_has_anonymous() throws Exception {
        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup/user"))
            .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    public void should_id_admin_return_true_when_has_blog_has_admin() throws Exception {
        Long id = userRepository.findOneByLogin("admin")
            .orElseThrow(() -> new UsernameNotFoundException("User admin not found"))
            .getId();

        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup/user"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string(id.toString()));
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

    @Test
    @WithMockUser(username="user",roles={"USER"})
    public void should_id_admin_return_forbiden_when_has_blog_has_admin() throws Exception {
        blog = createEntity(em);
        blogRepository.saveAndFlush(blog);
        restSetupMockMvc.perform(get("/api/setup/user"))
            .andExpect(status().isForbidden());
    }


}
