package com.abnamro;

import org.testng.annotations.AfterTest;
import util.RequestUtil;

import static config.Config.getProject;
import static util.RequestUtil.getIssues;

public class BaseTest {

    @AfterTest
    public void cleanupDataAfterTest(){
        getIssues(getProject()).forEach(RequestUtil::deleteIssue);
    }
}
