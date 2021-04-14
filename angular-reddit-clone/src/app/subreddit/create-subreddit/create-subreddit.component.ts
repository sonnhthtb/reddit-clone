import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SubredditModel } from '../subreddit-response';
import { SubredditService } from '../subreddit.service';

@Component({
    selector: 'app-create-subreddit',
    templateUrl: './create-subreddit.component.html',
    styleUrls: ['./create-subreddit.component.css'],
})
export class CreateSubredditComponent implements OnInit {
    createSubredditForm: FormGroup;
    subredditModel: SubredditModel;
    title = new FormControl('');
    description = new FormControl('');

    constructor(
        private router: Router,
        private subredditService: SubredditService,
        private toastr: ToastrService
    ) {
        this.createSubredditForm = new FormGroup({
            title: new FormControl('', Validators.required),
            description: new FormControl('', Validators.required),
        });
        this.subredditModel = {
            name: '',
            description: '',
        };
    }

    ngOnInit(): void {}

    discard() {
        this.router.navigateByUrl('/');
    }

    createSubreddit(subredditModel: SubredditModel) {
        this.subredditService.createSubreddit(subredditModel).subscribe(
            (data) => {
                this.toastr.success('Created subreddit!');
                this.router.navigateByUrl('/list-subreddits');
            },
            (error) => {
                console.log(error);
            }
        );
    }

    checkSubredditAndCreate() {
        this.subredditModel.name = this.createSubredditForm.get('title')?.value;
        this.subredditModel.description = this.createSubredditForm.get(
            'description'
        )?.value;
        this.subredditService.checkSubreddit(this.subredditModel).subscribe(
            () => {
                this.createSubreddit(this.subredditModel);
            },
            () => {
                this.toastr.error('Subreddit name already exists');
            }
        );
    }
}
