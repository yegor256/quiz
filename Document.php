<?php

class Document {
    public $user;
    public $name;

    public function __construct($name, $user) {
        assert(strlen($name) > 5);
        $this->user = $user;
        $this->name = $name;
    }

    public function getTitle() {
        return $this->getField('title');
    }

    public function getContent() {
        return $this->getField('content');
    }

    public static function getAllUserDocuments($user) {
        $db = Database::getInstance();
        if($result = $db->query('SELECT * FROM document')) {
            $documents = array();
            while($row = $result->fetch_assoc()) {
                $documents[] = new Document($row['name'], $user);
            }
            $result->close();
            return $documents;
        } else {
            return array();
        }
    }

    private function getField($field) {
        $db = Database::getInstance();
        $stmt = $db->prepare(
            'SELECT * FROM document WHERE name = ? LIMIT 1');
        if($stmt) {
            $stmt->bind_param('s', $this->name);
            $stmt->execute();
            $result = $stmt->get_result()->fetch_assoc();

            $stmt->close();
            if($result) {
                return $result[$field];
            } else {
                return '';
            }
        } else {
            return '';
        }
    }
}

class User {
    public function makeNewDocument($name) {
        return new Document($name, $this);
    }

    public function getMyDocuments() {
        return Document::getAllUserDocuments($this);
    }
}
