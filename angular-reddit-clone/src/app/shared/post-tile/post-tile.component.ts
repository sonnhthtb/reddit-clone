import { Component, Input, OnInit } from '@angular/core';
import {
    faArrowDown,
    faArrowUp,
    faComments,
} from '@fortawesome/free-solid-svg-icons';
import { PostModel } from '../post-model';
import { PostService } from '../post.service';
import { Router } from '@angular/router';
@Component({
    selector: 'app-post-tile',
    templateUrl: './post-tile.component.html',
    styleUrls: ['./post-tile.component.css'],
})
export class PostTileComponent implements OnInit {
    @Input() posts: PostModel[] = [];

    faArrowUp = faArrowUp;
    faArrowDown = faArrowDown;
    faComments = faComments;

    constructor(private postService: PostService, private router: Router) {}

    ngOnInit(): void {}

    goToPost(id: number): void {
        this.router.navigateByUrl('/view-post/' + id);
    }
}
