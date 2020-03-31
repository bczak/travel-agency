package RSP.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService
{
//     JEN KOSTRA
//    private UserDao userDao;
//
//    @Autowired
//    public UserService(UserDao userDao)
//    {
//        this.userDao = userDao;
//    }
//
//    public boolean add(User user)
//    {
//        if(user == null)
//            throw new NullPointerException("User can not be Null.");
//        if(alreadyExists(user.getId()))
//            return false;
//        userDao.add(user);
//        return true;
//    }
//
//    public boolean remove(int id)
//    {
//        User user = userDao.get(id);
//        if(user == null)
//            throw new NullPointerException("User can not be Null.");
//        if(!alreadyExists(user))
//            return false;
//        userDao.remove(user);
//        return true;
//    }
//
//    public User get(int id)
//    {
//        if(!alreadyExists(id))
//            throw new NullPointerException("User can not be Null.");
//        return userDao.get(id);
//    }
//
//    public List<User> getAll()
//    {
//        return userDao.getAll();
//    }
//
//    public User getByUsername(String username)
//    {
//        for(User user : userDao.getAll())
//        {
//            if(user.getUsername().equals(username)){
//                return user;
//            }
//        }
//        return null;
//    }
//
//    public boolean alreadyExists(User user)
//    {
//        return userDao.getAll().contains(user);
//    }
//
//    public boolean alreadyExists(int id)
//    {
//        return userDao.get(id) != null;
//    }
}
