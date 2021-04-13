import { Component, Input, OnInit } from '@angular/core';
import { PostModel } from '../post-model';
import { faArrowUp, faArrowDown } from '@fortawesome/free-solid-svg-icons';
import { VotePayload } from './vote-payload';
import { VoteService } from '../vote.service';
import { PostService } from '../post.service';
import { ToastrService } from 'ngx-toastr';
import { throwError } from 'rxjs';
import { VoteType } from './vote-type';
@Component({
    selector: 'app-vote-button',
    templateUrl: './vote-button.component.html',
    styleUrls: ['./vote-button.component.css'],
})
export class VoteButtonComponent implements OnInit {
    @Input() post: PostModel = new PostModel();
    votePayload: VotePayload = new VotePayload();
    faArrowUp = faArrowUp;
    faArrowDown = faArrowDown;
    upvoteColor: string = '';
    downvoteColor: string = '';

    constructor(
        private voteService: VoteService,
        private postService: PostService,
        private toastr: ToastrService
    ) {
        this.votePayload = {
            voteType: undefined,
            postId: undefined,
        };
    }

    ngOnInit(): void {
        this.updateVoteDetails();
    }

    upvotePost() {
        this.votePayload.voteType = VoteType.UPVOTE;
        this.vote();
    }

    downvotePost() {
        this.votePayload.voteType = VoteType.DOWNVOTE;
        this.vote();
    }

    private vote() {
        this.votePayload.postId = this.post.id;
        this.voteService.vote(this.votePayload).subscribe(
            () => {
                this.updateVoteDetails();
            },
            (error) => {
                this.toastr.error('Can not vote this post');
                throwError(error);
            }
        );
    }
    private updateVoteDetails() {
        this.postService.getPost(this.post.id).subscribe((post) => {
            this.post = post;
        });
    }
}
