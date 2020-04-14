package RSP.service;

import RSP.dao.TagDao;
import RSP.dao.TripDao;
import RSP.model.Tag;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {
    private TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public void add(Tag tag) {
        tagDao.add(tag);
    }

    public Tag get(int id) {
        return tagDao.get(id);
    }

    public Tag update(Tag tag) {
        if (tag != null) {
            return tagDao.update(tag);
        }
        return null;
    }

    public List<Tag> getAll() {
        return tagDao.getAll();
    }

    public void remove(int id) throws TripNotFoundException {
        Tag tag = tagDao.get(id);
        if (tag == null) {
            throw new IllegalArgumentException("tag must not be null");
        }
        tagDao.remove(tag);
    }
}
