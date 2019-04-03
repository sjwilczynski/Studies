<?php

namespace list3\sorters;




use list3\Application;

class PointsSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Application $application1, Application $application2) {
            return $application2->getPoints() - $application1->getPoints();
        });
        return $newList;
    }
}