package com.example.alwayswin.mapper;

import com.example.alwayswin.entity.Figure;
import com.example.alwayswin.mapper.FigureMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class FigureMapperTest {

    @Autowired
    private FigureMapper figureMapper;

    @Test
    void HappyPathWithGetFigureByFid() {
        Figure figure = figureMapper.getByFid(1);
        assertNotNull(figure);
        assertEquals("black", figure.getDescription());
    }

    @Test
    public void figureNotFoundWithGetByFid() {
        Figure figure = figureMapper.getByFid(0);
        assertNull(figure);
    }

    @Test
    void HappyPathWithGetFiguresByPid() {
        List<Figure> figureList = figureMapper.getByPid(1);
        assertNotNull(figureList);
        assertEquals(2, figureList.size());
    }
    @Test
    public void figureListNotFoundWithGetByPid() {
        List<Figure> figureList = figureMapper.getByPid(0);
        assertEquals(0, figureList.size());
    }

    @Test
    void HappyPathWithGetThumbnailByPid() {
        Figure thumbnail = figureMapper.getThumbnailByPid(2);
        assertNotNull(thumbnail);
        assertEquals("black", thumbnail.getDescription());
        assertTrue(thumbnail.isThumbnail());
    }

    @Test
    void HappyPathWithEditFigure() {
        Figure figure = figureMapper.getByFid(1);
        String originDesc = figure.getDescription();
        figure.setDescription("100% real");

        assertEquals(1, figureMapper.update(figure));

        figure = figureMapper.getByFid(1);
        assertEquals("100% real", figure.getDescription());

        figure.setDescription(originDesc);
        assertEquals(1, figureMapper.update(figure));

        figure = figureMapper.getByFid(1);
        assertEquals(originDesc, figure.getDescription());
    }

    @Test
    void HappyPathWithAddFigure() {
        Figure figure = new Figure(0, 1, "www.github.com", "Github",
                false, new Timestamp(System.currentTimeMillis()));
        int originCnt = figureMapper.getByPid(1).size();

        assertEquals(1, figureMapper.add(figure));
        assertEquals(originCnt + 1, figureMapper.getByPid(1).size());

        List<Figure> figureList = figureMapper.getByPid(1);
        assertNotNull(figureList);

        int fid = 0;
        for (Figure f: figureList)
            if (f.getUrl().equals("www.github.com"))
                fid = f.getFid();
        assertEquals(1, figureMapper.delete(fid));
        assertEquals(originCnt, figureMapper.getByPid(1).size());
    }

    @Test
    public void exceptionWithAddFigure() {
        // invalid uid
        Figure figure = new Figure(0, 0, "www.github.com", "Github",
                false, new Timestamp(System.currentTimeMillis()));

        assertThrows(Exception.class, () -> figureMapper.add(figure));
    }

    @Test
    void HappyPathWithDeleteFigure() {
        Figure figure = new Figure(0, 10, "www.github.com", "Github",
                false, new Timestamp(System.currentTimeMillis()));
        assertEquals(1, figureMapper.add(figure));

        List<Figure> figureList = figureMapper.getByPid(10);
        int fid = 0;
        for (Figure f: figureList)
            if (f.getUrl().equals("www.github.com"))
                fid = f.getFid();
        assertEquals(1, figureMapper.delete(fid));
    }
    
    @Test
    public void exceptionWithDeleteFigure() {
        assertEquals(0, figureMapper.delete(2000));
    }
}