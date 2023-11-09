package com.fsociety.warzone;

import com.fsociety.warzone.asset.AssetTests;
import com.fsociety.warzone.controller.ControllerTests;
import com.fsociety.warzone.model.ModelTests;
import com.fsociety.warzone.util.UtilTests;
import com.fsociety.warzone.view.ViewTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * The test suite for all the tests in the application
 */
@Suite
@SelectClasses({AssetTests.class, ControllerTests.class, ModelTests.class, UtilTests.class, ViewTests.class})
public class ApplicationTests {
}
