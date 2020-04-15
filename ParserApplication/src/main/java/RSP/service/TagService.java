package RSP.service;

import RSP.dao.TagDao;
import RSP.model.Tag;
import RSP.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TagService {
    private TagDao tagDao;

    @Autowired
    public TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public List<Tag> addAll(List<Tag> tags) {
        List<Tag> conflict = new ArrayList<>();
        for (Tag t : tags) {
            Tag old = add(t);
            if (old != null) {
                conflict.add(old);
            }
        }
        return conflict;
    }

    public Tag getByName(String name) {
        return tagDao.getByName(name);
    }

//---CRUD
    public Tag add(Tag tag) {
        Objects.requireNonNull(tag, "tag must not be null");
        Tag oldTag = tagDao.getByName(tag.getName());
        if (oldTag == null) {
            tagDao.add(tag);
        }
        return oldTag;
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

    public void remove(int id) {
        Tag tag = tagDao.get(id);
        if (tag == null) {
            throw new IllegalArgumentException("tag must not be null");
        }
        tagDao.remove(tag);
    }
}
