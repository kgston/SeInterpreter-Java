package com.sebuilder.interpreter.steptype;

import com.sebuilder.interpreter.StepType;
import com.sebuilder.interpreter.TestRun;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Selenium builder step that attempts to get a full page screenshot for chrome by expanding the viewport to match the entire page.
 * For other browsers it just passes through to the SaveScreenshot class.
 * This step checks for chrome through the browserName param in the driver configuration for RemoteWebDriver
 * @author Kingston Chan
 */
public class SaveScreenshotFull implements StepType {
    @Override
    public boolean run(TestRun ctx) {
        ctx.log().debug("Current browserName: " + ctx.getWebDriverConfig().get("browserName"));
        if("chrome".equalsIgnoreCase(ctx.getWebDriverConfig().get("browserName"))) {
            ctx.getDriver().manage().window().maximize();
            BufferedImage screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(ctx.getDriver())
                    .getImage();

            try {
                File outputFile = new File(ctx.string("file"));
                outputFile.createNewFile();
                return ImageIO.write(screenshot, "png", outputFile);
            } catch (IOException ioe) {
                throw new RuntimeException("Unable to save screenshot to: " + ctx.string("file"));
            }
        } else {
            return new SaveScreenshot().run(ctx);
        }
    }
}
