<?php

/**
 * Intentionally lightweight.
 *
 * TODO There should at least by an `id` property here. So that {@link Document} can properly reference self. That
 *      property can be used for easy queries.
 */
class User {

}

class Document {
    /**
     * @var User
     */
    public $user;

    /**
     * @var string
     */
    public $name;
    /**
     * @var string
     */
    public $title;
    /**
     * @var string
     */
    public $content;

    public function __construct($name, User $user = null) {
        // TODO Should throw an Exception instead especially if the $name comes from a user input.

        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    /**
     * @return Document[]
     */
    public static function findAll() {
        // TODO to be implemented later
        return [];
    }

    /**
     * @param string $name
     * @return Document
     */
    public static function findByName($name) {
        $db = Database::getInstance();
        $row = $db->query('SELECT * FROM document WHERE name = "' . mysql_real_escape_string($name) . '" LIMIT 1');

        if (!$row) {
            return null;
        }

        $doc = new Document($name);
        $doc->title = $row[3];
        $doc->content = $row[6];

        // TODO Set $doc->user as well

        return $doc;
    }

    /**
     * @param User $user
     * @return Document[]
     */
    public static function findAllForUser(User $user) {
        // TODO This needs to be changed into an actual query. Unfortunately, there is no defined way of how
        // `Document` references `User`.

        $list = [];
        foreach (self::findAll() as $doc) {
            // TODO this comparison may not be enough. Perhaps a `user->id` comparison would be more efficient and will
            // not provide false negatives.
            if ($doc->user == $user) {
                $list[] = $doc;
            }
        }
        return $list;
    }
}


