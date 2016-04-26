<?php

class Document {

    /**
     * User of Document.
     *
     * @var User
     */
    private $user;

    /**
     * Name of Document.
     *
     * @var string
     */
    private $name;

    /**
     * Constructor
     *
     * @return void
     */
    public function __construct(array $row)
    {
        $this->title = $row[3];
        $this->content = $row[6];
    }

    /**
     * Instantiates a new Document with a given name and User.
     *
     * @param  string $name Name.
     * @param  User   $user User
     *
     * @return void
     */
    public function init($name, User $user)
    {
        $this->setName($name);
        $this->setUser($user);
    }

    /**
     * Title of the current Document.
     *
     * @return string
     */
    public function getTitle(): string
    {
        return $this->title;
    }

    /**
     * Sets new Title for current Document.
     *
     * @param string $title
     *
     * @throws Exception Throws an Exception when name is too short.
     *
     * @return $this
     */
    public function setTitle(string $title): Document
    {
      if (!strlen($name) > 5) {
          throw new Exception("Name is too short.");
      }
      $this->title = $title;
      return $this;
    }

    /**
     * User of the current Document.
     *
     * @return User
     */
    public function getUser(): User
    {
        return $this->user;
    }

    /**
     * Sets User for current Document.
     *
     * @param User $user
     *
     * @return $this
     */
    public function setUser(User $user): Document
    {
      $this->user = $user;
      return $this;
    }

    /**
     * Content of the current Document.
     *
     * @return string
     */
    public function getContent(): string
    {
        return $this->content;
    }

    /**
     * Sets Content for current Document.
     *
     * @param string $content
     *
     * @return $this
     */
    public function setContent(string $content): Document
    {
      $this->content = $content;
      return $this;
    }

    /**
     * Fetches a Document by its name.
     *
     * @param  string $name
     *
     * @return Document
     */
    public static function fetchByName(string $name): Document
    {
      $db = Database::getInstance();
      $query = 'SELECT * FROM document WHERE name = `%s` LIMIT 1';
      $query = sprintf($query, $db->escape($name));
      $row = $db->query($query);

      return new self($row);
    }

    /**
     * Returns all Documents.
     *
     * @return Document[]
     */
    public static function getAllDocuments(): array
    {
        // to be implemented later
    }

}
