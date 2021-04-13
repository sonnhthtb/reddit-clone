import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostModel } from 'src/app/shared/post-model';
import { PostService } from 'src/app/shared/post.service';
import { SubredditModel } from '../subreddit-response';
import { SubredditService } from '../subreddit.service';

@Component({
    selector: 'app-view-subreddit',
    templateUrl: './view-subreddit.component.html',
    styleUrls: ['./view-subreddit.component.css'],
})
export class ViewSubredditComponent implements OnInit {
    posts: Array<PostModel> = [];
    subredditId!: number;
    subredditModel!: SubredditModel;
    constructor(
        private postService: PostService,
        private avtivatedRoute: ActivatedRoute,
        private subredditService: SubredditService
    ) {
        this.subredditId = this.avtivatedRoute.snapshot.params.id;
        this.postService
            .getAllPostsBySubreddit(this.subredditId)
            .subscribe((post) => {
                this.posts = post;
            });
        this.subredditService
            .getSubreddit(this.subredditId)
            .subscribe((data) => {
                this.subredditModel = data;
            });
    }

    ngOnInit(): void {}
}
