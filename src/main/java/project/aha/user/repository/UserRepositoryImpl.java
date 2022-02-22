package project.aha.user.repository;


public class UserRepositoryImpl   {
   /* private final EntityManager em;

    public UserRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
       User user = em.find(User.class, id);
       return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> result = em.createQuery("select u from user u where u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();


        return result.stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from user u", User.class).getResultList();
    }*/
}
